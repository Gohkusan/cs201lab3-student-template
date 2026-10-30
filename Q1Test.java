import java.util.ArrayList;
import java.util.List;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }

     private static final String[] NAMES = { "Inorder", "Preorder", "Postorder" };

     // every way to assign (0=Inorder, 1=Preorder, 2=Postorder) to traversal 1, 2, 3
     private static final int[][] PERMUTATIONS = {
               { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 }
     };

     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] t = new int[3][];
          try {
               t[0] = parse(traversal1);
               t[1] = parse(traversal2);
               t[2] = parse(traversal3);
          } catch (NumberFormatException e) {
               return "Invalid traversals";
          }

          for (int[] perm : PERMUTATIONS) {
               // in[k] = the traversal (0, 1 or 2) assigned to order k
               int[] in = new int[3];
               for (int i = 0; i < 3; i++) {
                    in[perm[i]] = i;
               }

               // a BST is uniquely rebuilt by inserting its preorder sequence
               Node root = null;
               for (int v : t[in[1]]) {
                    root = insert(root, v);
               }

               List<Integer> inorder = new ArrayList<>();
               List<Integer> preorder = new ArrayList<>();
               List<Integer> postorder = new ArrayList<>();
               inorder(root, inorder);
               preorder(root, preorder);
               postorder(root, postorder);

               if (matches(inorder, t[in[0]]) && matches(preorder, t[in[1]])
                         && matches(postorder, t[in[2]])) {
                    return "Traversal 1 - " + NAMES[perm[0]] + ", Traversal 2 - " + NAMES[perm[1]]
                              + ", Traversal 3 - " + NAMES[perm[2]];
               }
          }
          return "Invalid traversals";
     }

     private static int[] parse(String s) {
          String[] parts = s.trim().split("-");
          int[] values = new int[parts.length];
          for (int i = 0; i < parts.length; i++) {
               values[i] = Integer.parseInt(parts[i].trim());
          }
          return values;
     }

     private static Node insert(Node node, int v) {
          if (node == null) {
               return new Node(v);
          }
          if (v < node.value) {
               node.left = insert(node.left, v);
          } else if (v > node.value) {
               node.right = insert(node.right, v);
          }
          return node;
     }

     private static void inorder(Node node, List<Integer> out) {
          if (node == null) {
               return;
          }
          inorder(node.left, out);
          out.add(node.value);
          inorder(node.right, out);
     }

     private static void preorder(Node node, List<Integer> out) {
          if (node == null) {
               return;
          }
          out.add(node.value);
          preorder(node.left, out);
          preorder(node.right, out);
     }

     private static void postorder(Node node, List<Integer> out) {
          if (node == null) {
               return;
          }
          postorder(node.left, out);
          postorder(node.right, out);
          out.add(node.value);
     }

     private static boolean matches(List<Integer> actual, int[] expected) {
          if (actual.size() != expected.length) {
               return false;
          }
          for (int i = 0; i < expected.length; i++) {
               if (actual.get(i) != expected[i]) {
                    return false;
               }
          }
          return true;
     }
}