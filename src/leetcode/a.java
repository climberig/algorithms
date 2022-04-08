package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
public class a{
    static class s703{//Kth Largest Element in a Stream
        class KthLargest{
            PriorityQueue<Integer> q = new PriorityQueue<>();
            int k;

            public KthLargest(int k, int[] a){
                this.k = k;
                Arrays.stream(a).forEach(this::add);
            }

            public int add(int val){
                q.offer(val);
                if(q.size() > k)
                    q.poll();
                return q.peek();
            }
        }
    }
}
