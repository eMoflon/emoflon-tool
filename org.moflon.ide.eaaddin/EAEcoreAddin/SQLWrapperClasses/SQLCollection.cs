using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    [ComVisibleAttribute(false)]
    public class SQLCollection<T>
    {
        List<T> collection = new List<T>();
        #region IDualCollection Members

        public object AddNew(T newEntry)
        {
            collection.Add(newEntry);
            return newEntry;
        }
        public short Count
        {
            get 
            {
                return (short)collection.Count;
            }
        }

        public void Delete(short index)
        {
            collection.RemoveAt(index);
        }

        public void Delete(T oldEntry)
        {
            collection.Remove(oldEntry);
        }

        public void DeleteAt(short index, bool Refresh)
        {
            throw new NotImplementedException();
        }

        [DebuggerHidden]
        public T GetAt(short index)
        {
            if(collection.Count > 0)
                return collection[index];
            return default(T);
        }

        public System.Collections.IEnumerator GetEnumerator()
        {
            return new IEnumeratorImpl<T>(collection);
        }

        public string GetLastError()
        {
            throw new NotImplementedException();
        }

        public EA.ObjectType ObjectType
        {
            get { throw new NotImplementedException(); }
        }

        public void Refresh()
        {
            return;
        }

        #endregion

    }
}
