package com.lovehins.cache.utils;

import com.lovehins.cache.vo.CacheTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeUtils {
    public TreeUtils() {
    }

    public static List<CacheTree> buildTree(List<CacheTree> trees) {
        ArrayList list = new ArrayList();
        Iterator i$ = trees.iterator();

        while(i$.hasNext()) {
            CacheTree tree = (CacheTree)i$.next();
            if(tree.getParentId().equals("-1")) {
                list.add(tree);
            }

            Iterator i$1 = trees.iterator();

            while(i$1.hasNext()) {
                CacheTree t = (CacheTree)i$1.next();
                if(t.getParentId().equals(tree.getId())) {
                    if(tree.getNodes() == null) {
                        ArrayList myChildrens = new ArrayList();
                        myChildrens.add(t);
                        tree.setNodes(myChildrens);
                    } else {
                        tree.getNodes().add(t);
                    }
                }
            }
        }

        return list;
    }
}