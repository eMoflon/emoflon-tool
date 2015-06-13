/*Copyright*/
package de.uni_kassel.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parsing utility for character arrays.
 *
 * @see #CharArrayTool
 */
public class CharArrayTool
{
   private static final int START_DEPTH = 50;
   private final MarkupState endState = new MarkupState("END STATE", null, null);

   /**
    * Data class. Specifies start and end markup of a block, as well as escaping sequence.
    *
    * @see CharArrayTool#CharArrayTool
    */
   public static final class MarkupBlock
   {
      /**
       * Default constructor. Requires to set the individual fields.
       */
      public MarkupBlock()
      {
      }

      /**
       * Convenience constructor.
       *
       * @param startToken see {@link #startToken}
       * @param endToken   see {@link #endToken}
       */
      public MarkupBlock(String startToken, String endToken)
      {
         this.startToken = startToken;
         this.endToken = endToken;
      }

      /**
       * Convenience constructor.
       *
       * @param startToken         see {@link #startToken}
       * @param endToken           see {@link #endToken}
       * @param escapingInnerToken see {@link #escapingInnerToken}
       */
      public MarkupBlock(String startToken, String endToken, String escapingInnerToken)
      {
         this.startToken = startToken;
         this.endToken = endToken;
         this.escapingInnerToken = escapingInnerToken;
         this.allowsNestedBlocks = false;
      }

      /**
       * Convenience constructor.
       *
       * @param startToken         see {@link #startToken}
       * @param endToken           see {@link #endToken}
       * @param allowsNestedBlocks see {@link #allowsNestedBlocks}
       */
      public MarkupBlock(String startToken, String endToken, boolean allowsNestedBlocks)
      {
         this.startToken = startToken;
         this.endToken = endToken;
         this.allowsNestedBlocks = allowsNestedBlocks;
      }

      /**
       * Convenience constructor.
       *
       * @param startToken         see {@link #startToken}
       * @param endToken           see {@link #endToken}
       * @param allowsNestedBlocks see {@link #allowsNestedBlocks}
       */
      public MarkupBlock(String startToken, String endToken, boolean allowsNestedBlocks, boolean returnOnMisplacedEndToken)
      {
         this.startToken = startToken;
         this.endToken = endToken;
         this.allowsNestedBlocks = allowsNestedBlocks;
         this.returnOnMisplacedEndToken = returnOnMisplacedEndToken;
      }

      /**
       * Non-empty String which specifies the start sequence for this block. E.g. "//" to start a line comment.
       */
      public String startToken;
      /**
       * Non-empty String which specifies the end sequence for this block. E.g. "\n" to end a line comment.
       */
      public String endToken;
      /**
       * Token which escapes the end token in a block. E.g. "\\" in a character sequence block.
       */
      public String escapingInnerToken;
      /**
       * Defaults to true, to specify blocks in this block are valid.
       * False to ignore any starting blocks within this block.
       */
      public boolean allowsNestedBlocks = true;
      /**
       * True to stop searching if a misplaced end token is found
       * Defaults to false to ignore any starting misplaced end tokens.
       */
      public boolean returnOnMisplacedEndToken = false;
   }

   private static final MarkupState THIS_MARKER = new MarkupState(null,null,null);

   private final static class MarkupState
   {
      private MarkupState(String name, CharArrayTool.MarkupState popState, CharArrayTool.MarkupState stateReplacementOnNoMatch)
      {
         this(name, popState, stateReplacementOnNoMatch, false);
      }

      private MarkupState(String name, CharArrayTool.MarkupState popState, CharArrayTool.MarkupState stateReplacementOnNoMatch, boolean isEscapeState)
      {
         this.name = name;
         this.popState = popState;
         this.stateReplacementOnNoMatch = stateReplacementOnNoMatch == THIS_MARKER ? this : stateReplacementOnNoMatch;
         this.isEscapeState = isEscapeState;
      }

      final MarkupState popState;

      String name;
      final MarkupState[] nextStates = new MarkupState[256];
      final MarkupState stateReplacementOnNoMatch;
      final boolean isEscapeState;

      @Override
      public String toString()
      {
         return "MarkupState{" +
               "name='" + name + '\'' +
               '}';
      }
   }

   private final MarkupState rootState;

   /**
    * Initializes a CharArrayTool with a collection of block markers for {@link #indexOf(char[], char, int)}.
    *
    * @param blocks marker specifications
    * @see MarkupBlock
    */
   public CharArrayTool(MarkupBlock... blocks)
   {
      this(Arrays.asList(blocks));
   }

   /**
    * Initializes a CharArrayTool with a collection of block markers for {@link #indexOf(char[], char, int)}.
    *
    * @param blocks marker specifications
    * @see MarkupBlock
    */
   public CharArrayTool(Collection<MarkupBlock> blocks)
   {
      rootState = new MarkupState("root", null, THIS_MARKER);
      List<MarkupState> states = new ArrayList<MarkupState>();
      Map<MarkupState, MarkupState> alternatives = new HashMap<MarkupState, MarkupState>();
      if (blocks != null)
      {
         for (MarkupBlock block : blocks)
         {
            if (block.startToken == null || block.startToken.length() < 1 ||
                  block.endToken == null || block.endToken.length() < 1)
            {
               throw new IllegalArgumentException("neither end nor start token can be null or empty");
            }

            final MarkupState newState;
            {
               MarkupState last = rootState;
               for (int i = 0; i < block.startToken.length(); i++)
               {
                  char c = block.startToken.charAt(i);
                  String name = (i + 1) + " of " + block.startToken;
                  if (c >= 256)
                  {
                     throw new IllegalArgumentException("only characters with an ordinal below 256 are supported as markup");
                  }
                  MarkupState next = last.nextStates[c];
                  if (next == null)
                  {
                     next = new MarkupState(name, null, i < block.startToken.length()-1 ? null: THIS_MARKER);
                     states.add(next);
                     last.nextStates[c] = next;
                  } else
                  {
                     if (i == block.startToken.length() - 1)
                     {
                        throw new IllegalArgumentException("got two blocks with the same start token: " + block.startToken);
                     }
                     next.name += " and " + name;
                  }
                  last = next;
               }
               newState = last;
            }
            if (block.allowsNestedBlocks)
            {
               alternatives.put(newState, rootState);
            }


            if (block.escapingInnerToken != null && block.escapingInnerToken.length() > 0)
            {
               MarkupState last = newState;
               int length = block.escapingInnerToken.length();
               for (int i = 0; i < length; i++)
               {
                  char c = block.escapingInnerToken.charAt(i);
                  String name = (i + 1) + " of " + block.escapingInnerToken;
                  if (c >= 256)
                  {
                     throw new IllegalArgumentException("only characters with an ordinal below 256 are supported as markup");
                  }
                  MarkupState next = last.nextStates[c];
                  if (next == null)
                  {
                     next = new MarkupState(name, null, newState, i == length - 1 );
                     states.add(next);
                     last.nextStates[c] = next;
                  } else
                  {
                     if (i == block.escapingInnerToken.length() - 1)
                     {
                        throw new IllegalArgumentException("got two blocks with matching start and escaping token: " + block.startToken);
                     }
                     next.name += " and " + name;
                  }
                  last = next;
               }
            }


            {
               MarkupState last = newState;
               for (int i = 0; i < block.endToken.length(); i++)
               {
                  char c = block.endToken.charAt(i);
                  String name = (i + 1) + " of " + block.endToken;
                  if (c >= 256)
                  {
                     throw new IllegalArgumentException("only characters with an ordinal below 256 are supported as markup");
                  }
                  MarkupState next = last.nextStates[c];
                  if (next == null)
                  {
                     next = new MarkupState(name, i < block.endToken.length()-1 ? null: newState, newState);
                     states.add(next);
                     last.nextStates[c] = next;
                  } else
                  {
                     if (i == block.endToken.length() - 1)
                     {
                        throw new IllegalArgumentException("got two blocks with the same start token: " + block.startToken);
                     }
                     next.name += " and " + name;
                  }
                  last = next;
               }
            }
            {
               if (block.returnOnMisplacedEndToken && !block.startToken.equals(block.endToken))
               {
                  MarkupState last = rootState;
                  for (int i = 0; i < block.endToken.length(); i++)
                  {
                     char c = block.endToken.charAt(i);
                     String name = (i + 1) + " of " + block.endToken;
                     if (c >= 256)
                     {
                        throw new IllegalArgumentException("only characters with an ordinal below 256 are supported as markup");
                     }
                     MarkupState next = last.nextStates[c];
                     if (next == null)
                     {
                        next = i == block.endToken.length()-1 ? endState : new MarkupState(name, null, null);
                        states.add(next);
                        last.nextStates[c] = next;
                     } else
                     {
                        if (i == block.endToken.length() - 1)
                        {
                           throw new IllegalArgumentException("got two blocks with the same start token: " + block.startToken);
                        }
                        next.name += " and " + name;
                     }
                     last = next;
                  }
               }
            }
         }         
      }

      for (final MarkupState state : states)
      {
         CharArrayTool.MarkupState alternative = alternatives.get(state);
         if (alternative != null)
         {
            for (int i = 0; i < alternative.nextStates.length; i++)
            {
               MarkupState value = alternative.nextStates[i];
               if (state.nextStates[i] == null)
               {
                  state.nextStates[i] = value;
               }
            }
         }
      }
   }

   /**
    * Search for the first occurrence of character which is not in a block started by the block markers of this tool.
    *
    * @param data        character array to search through
    * @param character   char to find
    * @param startOffset where to start searching
    * @return index where character was found outside of a block
    */
   public int indexOf(char[] data, char character, int startOffset)
   {
      return indexOf(data, character, startOffset, data.length, true,  true, false);
   }

   /**
    * Search for the first occurrence of character which is not in a block started by the block markers of this tool.
    *
    * @param data        character array to search through
    * @param character   char to find
    * @param startOffset where to start searching
    * @param endOffset   where to stop searching
    * @return index where character was found outside of a block
    */
   public int indexOf(char[] data, char character, int startOffset, int endOffset)
   {
      return indexOf(data, character, startOffset, endOffset, true, true, false);
   }

   /**
    * Search for the first occurrence of character which is not in a block started by the block markers of this tool.
    *
    * @param data          character array to search through
    * @param character     char to find
    * @param startOffset   where to start searching
    * @param endOffset     where to stop searching
    * @param matchEndToken indicates whether to return the indexOf for a {@link MarkupBlock#endToken}
    * @return index where character was found outside of a block
    */
   public int indexOf(char[] data, char character, int startOffset, int endOffset, boolean matchEndToken)
   {
      return indexOf(data, character, startOffset, endOffset, true, matchEndToken, false);
   }
   
   /**
    * Search for the first occurrence of character which is not in a block started by the block markers of this tool.
    *
    * @param data            character array to search through
    * @param character       char to find
    * @param startOffset     where to start searching
    * @param endOffset       where to stop searching
    * @param matchStartToken indicates whether to return the indexOf for a {@link MarkupBlock#startToken}
    * @param matchEndToken   indicates whether to return the indexOf for a {@link MarkupBlock#endToken}
    * @param range           indicates whether to match a range of characters, beginning after character (return first position where data[pos] > character).
    * @return index where character was found outside of a block
    */
   public int indexOf(char[] data, char character, int startOffset, int endOffset, boolean matchStartToken, boolean matchEndToken, boolean range)
   {
      MarkupState[] stack = new MarkupState[START_DEPTH];
      int stackPos = 0;
      MarkupState state = rootState;
      stack[stackPos++] = state;

      int storedIndex = -1;
      endOffset = endOffset < data.length ? endOffset : data.length;
      for (int index = startOffset; index < endOffset; index++)
      {
         char current = data[index];
         if (state == rootState && (!range ? character == current : character < current))
         {
            if( matchStartToken )
            {
               return index;
            } else
            {
               storedIndex = index;
            }
         }
         MarkupState nextState = current < 256 ? state.nextStates[current] : null;
         if (nextState != null)
         {
            if (nextState == endState)
            {
               return -1;
            }
            MarkupState popState = nextState.popState;
            if (popState != null)
            {
               MarkupState popped = stack[--stackPos];
               while (popped != popState)
               {
                  if (popped.stateReplacementOnNoMatch == popped)
                  {
                     throw new IllegalStateException("popped '" + popped + "' while expecting '" + popState + "'");
                  }
                  popped = stack[--stackPos];
               }
               state = stack[stackPos - 1];
            } else
            {
               state = nextState;

               if (state.stateReplacementOnNoMatch == state)
               {
                  stack[stackPos++] = state;
                  if (stackPos >= stack.length)
                  {
                     stack = enlarge(stack);
                  }
                  storedIndex = -1;
               }
            }
         } else if (state.stateReplacementOnNoMatch != state)
         {
            MarkupState last = state;
            state = state.stateReplacementOnNoMatch;
            if (state == null)
            {
               state = stack[stackPos - 1];
               index--; // current char might be a delimiter, so try again in new state
               continue;
            } else if (stack[stackPos - 1] != state)
            {
               stack[stackPos++] = state;
               if (stackPos >= stack.length)
               {
                  stack = enlarge(stack);
               }
            } else if (!last.isEscapeState) {
               index--; // current char might be a delimiter, so try again in new state
               continue;               
            }
            if( storedIndex != -1 && state == rootState )
            {
               return storedIndex;
            } else
            {
               storedIndex = -1;
            }
         } else
         {
            if( storedIndex != -1)
            {
               return storedIndex;
            }
         }
         if (matchEndToken && state == rootState && (!range ? character == current : character < current))
         {
            return index;
         }
      }
      return -1;
   }

   private MarkupState[] enlarge(MarkupState[] stack)
   {
      MarkupState[] newStack = new MarkupState[stack.length + START_DEPTH];
      System.arraycopy(stack, 0, newStack, 0, stack.length);
      stack = newStack;
      return stack;
   }

   /**
    * Create a new char-wise matcher.
    * @param matchStartToken should start token be part of the match?
    * @param matchEndToken should end token be part of the match?
    * @return the matcher
    */
   public Matcher getMatcher(boolean matchStartToken, boolean matchEndToken)
   {
      return new CharArrayMatcher(matchStartToken, matchEndToken);
   }

   private final class CharArrayMatcher implements Matcher
   {
      /**
       * If an opening delimiter is found, the current state is pushed on the stack and poppen on a closing delimiter.
       */
      private MarkupState[] stack;
      /**
       * the current position on the stack
       */
      private int stackPos;
      /**
       * represents the state the matcher currently is in.
       */
      private MarkupState state;
      /**
       * should start token be part of the match?
       */
      private final boolean matchStartToken;
      /**
       * should end token be part of the match?
       */
      private final boolean matchEndToken;

      public CharArrayMatcher(boolean matchStartToken, boolean matchEndToken)
      {
         this.matchStartToken = matchStartToken;
         this.matchEndToken = matchEndToken;
         stack = new MarkupState[START_DEPTH];
         reset();
      }

      public void reset()
      {
         stackPos = 0;
         state = rootState;
         stack[stackPos++] = state;
      }

      public int indexOf(char current, char[] data, int offset)
      {
         boolean lookAhead = false;
         int index = offset;
         boolean startTokenFound = false;
         do
         {
            if (state == rootState)
            {
               if( matchStartToken )
               {
                  startTokenFound = true;
               } else
               {
                  lookAhead = true;
               }
            }
            MarkupState nextState = current < 256 ? state.nextStates[current] : null;
            if (nextState != null)
            {
               if (nextState == endState)
               {
                  return -1;
               }
               MarkupState popState = nextState.popState;
               if (popState != null)
               {
                  MarkupState popped = stack[--stackPos];
                  while (popped != popState)
                  {
                     if (popped.stateReplacementOnNoMatch == popped)
                     {
                        throw new IllegalStateException("popped '" + popped + "' while expecting '" + popState + "'");
                     }
                     popped = stack[--stackPos];
                  }
                  state = stack[stackPos - 1];
               } else
               {
                  state = nextState;

                  if (state.stateReplacementOnNoMatch == state)
                  {
                     stack[stackPos++] = state;
                     if (stackPos >= stack.length)
                     {
                        stack = enlarge(stack);
                     }
                     if( startTokenFound )
                     {
                        return 0;
                     }
                     return index - offset + 1;
                  }
               }
            } else if (state.stateReplacementOnNoMatch != state)
            {
               MarkupState last = state;
               state = state.stateReplacementOnNoMatch;
               if (state == null)
               {
                  state = stack[stackPos - 1];
                  continue; // current char might be a delimiter, so try again in new state
               } else if (stack[stackPos - 1] != state)
               {
                  stack[stackPos++] = state;
                  if (stackPos >= stack.length)
                  {
                     stack = enlarge(stack);
                  }
               } else if (!last.isEscapeState)
               {
                  continue; // current char might be a delimiter, so try again in new state
               }
               if( lookAhead && state == rootState )
               {
                  return 0;
               } else
               {
                  lookAhead = false;
               }
            } else
            {
               if( lookAhead )
               {
                  return 0;
               }
            }
            if( startTokenFound )
            {
               return 0;
            }
            if (matchEndToken && state == rootState)
            {
               return index - offset;
            }
            if (!lookAhead)
            {
               return index - offset + 1;
            }
            index++;
            if (index >= data.length)
            {               
               return -1;
            }
            current = data[index];
         } while (true);
      }
   }

   /**
    * Interface for a char-wise matcher.
    * 
    * @author Leif Geiger
    */
   public interface Matcher
   {
      /**
       * Read the next character.
       * 
       * @param current the character that should be dealt with. This might change the internal state if the char is a delimiter.
       * @param data the char array that contains the current char. Needed for lookahead.
       * @param offset the position of the current char in data.
       * @return 0 if the matcher currently matches,n if it does not match, whereas n indicates the number of chars to skip, -1 if the matcher would never match again.
       */
      public int indexOf(char current, char[] data, int offset);// TODO rename

      /**
       * Resets the matchers internal state.
       */
      public void reset();
   }
}

/*
 * $Log$
 * Revision 1.23  2009/01/14 18:48:46  l3_g5
 * working but slow solution
 *
 * Revision 1.22  2009/01/14 17:55:02  l3_g5
 * still some problems
 *
 * Revision 1.21  2009/01/14 17:23:42  l3_g5
 * bug if end char occurs after partial delimiter
 *
 * Revision 1.20  2009/01/12 14:13:03  l3_g5
 * delimiter after partial delimiter working
 *
 * Revision 1.19  2009/01/12 10:36:08  l3_g5
 * some comments
 *
 * Revision 1.18  2009/01/09 15:26:43  l3_g5
 * working
 *
 * Revision 1.17  2009/01/09 15:06:33  cschneid
 * fixed rootState
 *
 * Revision 1.16  2009/01/09 14:47:38  l3_g5
 * changed signature
 *
 * Revision 1.15  2009/01/09 14:24:16  l3_g5
 * added char wise analysis
 *
 * Revision 1.14  2009/01/09 10:38:23  cschneid
 * little more optimization and test warmup
 *
 * Revision 1.12  2009/01/09 08:42:36  l3_g5
 * stop on misplaced end token
 *
 * Revision 1.11  2008/12/17 10:43:48  mbork
 * bugfix concerning not-closed blocks
 *
 * Revision 1.10  2008/12/17 09:49:00  mbork
 * - return endOffset if eof is reached and the current state is the root state
 * - added a test that points to a bug
 *
 * Revision 1.9  2008/12/16 17:28:06  mbork
 * toggle matching of start token
 *
 * Revision 1.8  2008/12/11 21:28:56  cschneid
 * fixed stack position on no match
 *
 * Revision 1.7  2008/12/11 15:21:33  mbork
 * fixed a bug
 *
 * Revision 1.6  2008/12/11 13:41:52  mbork
 * toggle matching of end token
 *
 * Revision 1.5  2008/12/10 16:57:12  mbork
 * end offset
 *
 * Revision 1.4  2008/12/10 15:52:08  cschneid
 * performance improvement
 *
 * Revision 1.3  2008/12/10 13:42:18  mbork
 * fixed typo
 *
 * Revision 1.2  2008/12/10 13:37:01  cschneid
 * added performance test, match closing tokens
 *
 * Revision 1.1  2008/12/10 12:56:49  cschneid
 * CharArrayUtil
 *
 */

