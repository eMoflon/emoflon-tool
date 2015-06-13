using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    class IEnumeratorImpl<T> : IEnumerator
    {
        #region IEnumerator Members

        List<T> listOfElements;
        int position = -1;
        public IEnumeratorImpl(List<T> listOfElements) 
        {
            this.listOfElements = listOfElements;
        }

        public object Current
        {
            get
            {
                try
                {
                    return listOfElements[position];
                }
                catch (IndexOutOfRangeException)
                {
                    throw new InvalidOperationException();
                }
            }
        }

        public bool MoveNext()
        {
            position++;
            return (position < listOfElements.Count);
        }

        public void Reset()
        {
            position = -1;
        }

        #endregion
    }
}
