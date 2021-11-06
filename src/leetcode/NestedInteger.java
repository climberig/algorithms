package leetcode;

import java.util.*;
import java.util.stream.*;

public interface NestedInteger {
    boolean isInteger();

    Integer getInteger();

    List<NestedInteger> getList();

    class ListIml implements NestedInteger {
        final List<NestedInteger> list;
        public ListIml(List<NestedInteger> list) {this.list = list;}
        public ListIml(Integer... vals) {
            list = Arrays.stream(vals).map(NestedInteger::of).collect(Collectors.toList());
        }

        @Override public boolean isInteger() {return false;}
        @Override public Integer getInteger() {return null;}
        @Override public List<NestedInteger> getList() {return list;}
    }

    class Val implements NestedInteger {
        final Integer val;
        public Val(int val) {this.val = val;}
        @Override public boolean isInteger() {return true;}
        @Override public Integer getInteger() {return val;}
        @Override public List<NestedInteger> getList() {return null;}
    }

    static NestedInteger of(int val) {return new Val(val);}

    static NestedInteger of(List<NestedInteger> list) {
        return new ListIml(list);
    }

    static NestedInteger of(Integer... vals) {
        return new ListIml(vals);
    }
}
