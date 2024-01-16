package leetcode;
public class p30{
    static class s3005{//Count Elements With Maximum Frequency
        public int maxFrequencyElements(int[] a) {
            int freq[] = new int[101], maxFreq = 0, r = 0;
            for (int n : a)
                if (++freq[n] == maxFreq)
                    r += maxFreq;
                else if (freq[n] > maxFreq) {
                    maxFreq = freq[n];
                    r = maxFreq;
                }
            return r;
        }
    }
}
