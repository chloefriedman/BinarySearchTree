import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  
 * @version 
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST

    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = null;
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
        return (root == null);
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        return size(root);
    }
    private int size(BinaryNode<T> tree)
    {
        if (tree == null)
            return 0;
        List<T> b = new ArrayList<T>();
        return inorderTraverse(tree, b).size();
    }
    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        if(root == null)
        {
            root = new BinaryNode<T>(element);
        }
        else if(contains(element))
        {
            return;
        }
        else
        {
            add(root,element);
        }
    }

    private void add(BinaryNode<T> current, T element)
    {
        boolean left = false;
        {
            if(current.getInfo().compareTo(element)>0)
               {
                    left = true;
               }
            if(left)
            {
                if(current.getLeft()!=null)
                {
                    add(current.getLeft(),element);
                }
                else{
                    current.setLeft(new BinaryNode<T>(element));
                }
            }
            else
            {
                if(current.getRight()!=null)
                {
                    add(current.getRight(),element);
                }
                else{
                    current.setRight(new BinaryNode<T>(element));
                }
            }
        }

    }

    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        BinaryNode<T> current = root;
        return contains(current, element);
    }

    private boolean contains(BinaryNode<T> c, T element)
    {
        if(c == null)
        {
            return false;
        }
        BinaryNode<T> current = c;
        if(element.compareTo(current.getInfo()) == 0)
        {
            return true;
        }
        else if (element.compareTo(current.getInfo()) > 0)
        {
            return contains(c.getRight(), element);
        }
        else
        {
            return contains(c.getLeft(), element);
        }
    }
    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    // Returns a list of elements from a preorder traversal
    //root left right
    public List<T> preorderTraverse()
    {
        List<T> a = new ArrayList<>();
        return preorderTraverse(root, a);
    }

    // Returns a list of elements from an inorder traversal
    public List<T> inorderTraverse()
    {
        List<T> a = new ArrayList<>();
        return inorderTraverse(root, a);
    }

    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> a = new ArrayList<>();
        return postorderTraverse(root, a);
    }

    private List<T> preorderTraverse(BinaryNode<T> next, List<T> b)
    {
        if(next !=null)
        {
            b.add(next.getInfo());
            preorderTraverse(next.getLeft(), b);
            preorderTraverse(next.getRight(), b);
        }
        return b;
    }

    private List<T> postorderTraverse(BinaryNode<T> next, List<T> b)
    {
        if(next !=null)
        {
            postorderTraverse(next.getLeft(), b);
            postorderTraverse(next.getRight(), b);
            b.add(next.getInfo());
        }
        return b;
    }

    private List<T> inorderTraverse(BinaryNode<T> next, List<T> b)
    {
        if(next !=null)
        {
            inorderTraverse(next.getLeft(), b);
            b.add(next.getInfo());
            inorderTraverse(next.getRight(), b);
        }
        return b;
    }

    // Removes an element e from this BST such that e.compareTo(element) == 0
    public void remove (T element)
    {
        BinaryNode<T> target = null;
        BinaryNode<T> current = root;
        BinaryNode<T> previous = null;
        boolean left = false;
        if(contains(element) == false)
        {
            return;
        }
        if(current.getInfo().equals(element))
        {
           if(current.getLeft() == null)
           {
                if(current.getRight() == null)
                {
                    root = null;
                }
                else
                {
                    root = current.getRight();
                    current.setRight(null);
                }
           }
           else
           {
                 previous = current;
                current = current.getLeft();
                while(current.getRight() != null)
                {
                    previous = current;
                    current = current.getRight();
                }
                root.setInfo(current.getInfo());
                previous.setRight(null);

           }
        }
        else
        {
            while(current.getInfo().compareTo(element) != 0)
            {
                if(element.compareTo(current.getInfo()) > 0)
                {
                    previous = current;
                    current = current.getRight();
                    left = false;
                }
                else
                {
                    previous = current;
                    current = current.getLeft();
                    left = true;
                }
            }
            target = current;
            if(target.getLeft() == null)
            {
                if(target.getRight() == null)
                {
                    if(left)
                    {
                        previous.setLeft(null);
                    }
                    else
                    {
                        previous.setRight(null);
                    }
                }
                else
                {
                   if(left)
                   {
                        previous.setLeft(target.getRight());
                   }
                   else
                   {
                      previous.setRight(target.getRight());
                   }
                }
            }
        }
    }

      // Restructures this BST to be optimally balanced
    //copy tree into an array with inorder traverse
    //set middle to root, then add all other elements in order
    public void balance()
    {
        
        List<T> a = new ArrayList<>();
        a = inorderTraverse(root, a);
        if(a.size() <= 1)
        {
            return;
        }
        else
        {
            root = null;
            balance(a, 0, a.size() - 1);
        }
        
    }
    //get the high and low index of range. find the middle and add it to the list
    private void balance(List<T> a, int low, int high)
    {
        if(low == high)
        {
            add(a.get(low));
        }
        else if(low == high - 1)
        {
            add(a.get(low));
            add(a.get(high));
        }
        else if(low > high)
        {
            return;
        }
        else
        {
            int middle = (low + high)/2;
            add(a.get(middle));
            balance(a, low,middle - 1);
            balance(a, middle + 1,high);
        }
    }
}