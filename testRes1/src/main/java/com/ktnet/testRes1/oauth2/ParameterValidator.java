package com.ktnet.testRes1.oauth2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ParameterValidator {
    public static String validateInput(HashMap hashMap) {
        HashSet hashSet = new HashSet();
        Iterator iterator = hashMap.keySet().iterator();
        while(iterator.hasNext()){
            String key = (String) iterator.next();
            String value = (String) hashMap.get(key);
            if((value == null) || ("".equals(value))){
                hashSet.add(key);
            }
        }
        if(hashSet.isEmpty()){
            return null;
        }
        Object localObject1 = new StringBuffer("Missing inputs : ");
        Object localObject2 = hashSet.iterator();
        while(((Iterator)localObject2).hasNext()){
            ((StringBuffer)localObject1).append(((Iterator)localObject2).next()).append(" ");
        }
        return ((StringBuffer)localObject1).toString().trim();
    }
}
