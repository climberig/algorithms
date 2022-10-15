package leetcode;
import java.util.*;
import java.util.stream.IntStream;
public class p24{
    static class s2401{//Longest Nice Subarray
        public int longestNiceSubarray(int[] a){
            int r = 1, and = 0;
            for(int i = 0, j = 0; j < a.length; j++){
                while((and & a[j]) > 0)
                    and ^= a[i++];
                and |= a[j];
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s2404{//Most Frequent Even Element
        public int mostFrequentEven(int[] a){
            Map<Integer, Integer> fr = new TreeMap<>();
            int maxFr = 0;
            for(int n : a)
                if(n % 2 == 0){
                    fr.put(n, fr.getOrDefault(n, 0) + 1);
                    maxFr = Math.max(maxFr, fr.get(n));
                }
            final int max = maxFr;
            return fr.keySet().stream().filter(n -> fr.get(n) == max).findFirst().orElse(-1);
        }
    }

    static class s2405{//Optimal Partition of String
        public int partitionString(String s){
            int r = 0;
            for(int i = 0, j; i < s.length(); i = j){
                boolean[] used = new boolean[26];
                for(j = i; j < s.length() && !used[s.charAt(j) - 'a']; j++)
                    used[s.charAt(j) - 'a'] = true;
                r++;
            }
            return r;
        }
    }

    static class s2408{//Design SQL
        class SQL{
            Map<String, Integer> id = new HashMap<>();
            Map<String, Map<Integer, List<String>>> data = new HashMap<>();

            public SQL(List<String> names, List<Integer> columns){
                names.forEach(name -> data.put(name, new HashMap<>()));
                names.forEach(name -> id.put(name, 1));
            }

            public void insertRow(String name, List<String> row){
                data.get(name).put(id.get(name), row);
                id.put(name, id.get(name) + 1);
            }

            public void deleteRow(String name, int rowId){}

            public String selectCell(String name, int rowId, int columnId){
                return data.get(name).get(rowId).get(columnId - 1);
            }
        }
    }

    static class s2410{//Maximum Matching of Players With Trainers
        public int matchPlayersAndTrainers(int[] players, int[] trainers){
            Arrays.sort(players);
            Arrays.sort(trainers);
            int r = 0;
            for(int i = 0, j = 0; i < players.length && j < trainers.length; )
                if(players[i] <= trainers[j]){
                    r++;
                    i++;
                    j++;
                }else j++;
            return r;
        }
    }

    static class s2413{//Smallest Even Multiple
        public int smallestEvenMultiple(int n){return n % 2 == 0 ? n : n * 2;}
    }

    static class s2414{//Length of the Longest Alphabetical Continuous Substring
        public int longestContinuousSubstring(String s){
            int r = 1;
            for(int i = 1, conseq = 1; i < s.length(); i++)
                if(s.charAt(i - 1) + 1 == s.charAt(i))
                    r = Math.max(r, ++conseq);
                else conseq = 1;
            return r;
        }
    }

    static class s2418{//Sort the People
        public String[] sortPeople(String[] names, int[] heights){
            int[][] m = new int[heights.length][2];
            for(int i = 0; i < heights.length; i++)
                m[i] = new int[]{heights[i], i};
            Arrays.sort(m, (a, b) -> b[0] - a[0]);
            return IntStream.range(0, names.length).mapToObj(i -> names[m[i][1]]).toArray(String[]::new);
        }
    }

    static class s2419{//Longest Subarray With Maximum Bitwise AND
        public int longestSubarray(int[] a){
            int max = Arrays.stream(a).max().getAsInt(), r = 1;
            for(int i = 1, count = 1; i < a.length; i++)
                if(a[i - 1] == max && a[i] == max)
                    r = Math.max(r, ++count);
                else count = 1;
            return r;
        }
    }

    static class s2422{//Merge Operations to Turn Array Into a Palindrome
        public int minimumOperations(int[] a){
            int r = 0, left = a[0], right = a[a.length - 1];
            for(int i = 0, j = a.length - 1; i < j; )
                if(left == right){
                    left = a[++i];
                    right = a[--j];
                }else if(left < right){
                    left += a[++i];
                    r++;
                }else{
                    right += a[--j];
                    r++;
                }
            return r;
        }
    }

    static class s2423{//Remove Letter To Equalize Frequency
        public boolean equalFrequency(String word){
            int[] f = new int[26];
            word.chars().forEach(c -> f[c - 'a']++);
            TreeMap<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(f).filter(c -> c > 0).forEach(c -> m.put(c, m.getOrDefault(c, 0) + 1));
            if(m.size() == 1 && (m.firstKey() == 1 || m.firstEntry().getValue() == 1))
                return true;
            if(m.size() != 2)
                return false;
            return Math.abs(m.firstKey() - m.lastKey()) == 1 && (m.firstEntry().getValue() == 1 || m.lastEntry().getValue() == 1);
        }
    }

    static class s2424{//Longest Uploaded Prefix
        class LUPrefix{
            Set<Integer> s = new HashSet<>();
            int max = 0;

            public LUPrefix(int n){}

            public void upload(int video){
                s.add(video);
                while(s.contains(max + 1))
                    max++;
            }

            public int longest(){return max;}
        }
    }

    static class s2428{//Maximum Sum of an Hourglass
        public int maxSum(int[][] g){
            int r = 0;
            for(int i = 0; i + 3 <= g.length; i++)
                for(int j = 0; j + 3 <= g[0].length; j++)
                    r = Math.max(r, g[i][j] + g[i][j + 1] + g[i][j + 2] + g[i + 1][j + 1] + g[i + 2][j] + g[i + 2][j + 1] + g[i + 2][j + 2]);
            return r;
        }
    }

    static class s2432{//The Employee That Worked on the Longest Task
        public int hardestWorker(int n, int[][] logs){
            int r = logs[0][0], maxTime = logs[0][1];
            for(int i = 1; i < logs.length; i++)
                if(logs[i][1] - logs[i - 1][1] == maxTime)
                    r = Math.min(r, logs[i][0]);
                else if(logs[i][1] - logs[i - 1][1] > maxTime){
                    maxTime = logs[i][1] - logs[i - 1][1];
                    r = logs[i][0];
                }
            return r;
        }
    }

    static class s2433{//Find The Original Array of Prefix Xor
        public int[] findArray(int[] pref){
            int[] r = new int[pref.length];
            r[0] = pref[0];
            for(int i = 1; i < r.length; i++)
                r[i] = pref[i] ^ pref[i - 1];
            return r;
        }
    }

    static class s2436{//Minimum Split Into Subarrays With GCD Greater Than One
        public int minimumSplits(int[] a){
            int r = 1, gcd = a[0];
            for(int n : a){
                gcd = gcd(n, gcd);
                if(gcd == 1){
                    r++;
                    gcd = n;
                }
            }
            return r;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2437{//Number of Valid Clock Times
        public int countTime(String time){
            if(!time.contains("?")){
                int h = Integer.parseInt(time.substring(0, 2));
                int m = Integer.parseInt(time.substring(3, 5));
                return 0 <= h && h <= 23 && 0 <= m && m < 60 ? 1 : 0;
            }else{
                int r = 0;
                for(int d = 0; d <= 9; d++)
                    r += countTime(time.replaceFirst("\\?", d + ""));
                return r;
            }
        }
    }
}