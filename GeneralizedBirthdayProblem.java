import java.util.*;

class GeneralizedBirthdayProblem {
  int n, k;

  GeneralizedBirthdayProblem(int n, int k) {
    this.n = n;
    this.k = k;

    int nn = (int) Math.pow(2, n/k + 1);

    // Initialize a list with random integers and print them
    Random random = new Random();
    int[] list = new int[nn];
    ArrayList<int[]> indices = new ArrayList<int[]>();
    for (int i = 0; i < nn; i++) {
      list[i] = random.nextInt(((int) Math.pow(2, n)) - 1);
      indices.add(new int[]{i});
      System.out.println("X" + (i + 1) + ": " + String.format("%" + n + "s", Integer.toBinaryString(list[i])).replace(" ", "0"));
    }

    // Search for collisions in each of k parts of each element
    for (int i = 0; i < k; i++) {
      // Initialize a temporary list with XORed parts of list elements
      int[] tmpList = new int[indices.size()];
      ArrayList<int[]> tmpIndices = new ArrayList<int[]>();
      for (int j = 0; j < indices.size(); j++) {
        tmpList[j] = getPart(list[indices.get(j)[0]], i);
        for (int l = 1; l <= ((int) Math.pow(2, i)) - 1; l++) {
          tmpList[j] = tmpList[j] ^ getPart(list[indices.get(j)[l]], i);
        }
      }

      // Search the temporary list for collisions
      for (int j = 0; j < indices.size(); j++) {
        for (int l = j + 1; l < indices.size(); l++) {
          if (tmpList[j] == tmpList[l]) {
            int length = indices.get(j).length;
            // Check if some index would be duplicate
            boolean duplicate = false;
            for (int m = 0; m < length; m++) {
              for (int o = 0; o < length; o++) {
                if (indices.get(j)[m] == indices.get(l)[o]) {
                  duplicate = true;
                }
              }
            }
            if (!duplicate) {
              int[] newIndex = new int[2 * length];
              System.arraycopy(indices.get(j), 0, newIndex, 0, length);
              System.arraycopy(indices.get(l), 0, newIndex, length, length);
              tmpIndices.add(newIndex);
            }
          }
        }
      }
      indices = tmpIndices;
    }

    // Sort indices in solutions
    for (int[] index : indices) {
      Arrays.sort(index);
    }

    // Print solutions
    for (int i = 0; i < indices.size(); i++) {
      String indicesString = new String();
      for (int j = 0; j <= ((int) Math.pow(2, k)) - 1; j++) {
        indicesString = indicesString + " X" + Integer.toString(indices.get(i)[j] + 1);
      }
      System.out.println("Solution " + (i + 1) + ":" + indicesString);
    }
  }

  // Extract a part of an element using bitwise operations
  int getPart(int element, int part) {
    int start = (int) part*(n/k) + 1;
    int count = (int) n/k;
    return (((1 << count) - 1) & (element >> (start - 1)));
  }

  public static void main(String[] args) {
    new GeneralizedBirthdayProblem(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
  }
}

