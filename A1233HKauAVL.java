package cis233.a1;
// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import weiss.nonstandard.LinkedList;
import weiss.nonstandard.LinkedListIterator;

import java.io.*;
import java.util.ArrayList;


/**
     * Implements an AVL tree.
     * Note that all "matching" is based on the compareTo method.
     * @author Mark Allen Weiss
     */
    public class A1233HKauAVL<AnyType extends Comparable<? super AnyType>> {

        /**
        *  Prints name
        */
        public String author()
        {
            return "Haedon Kaufman";
        }

        /**
         * Construct the tree.
         */
        public A1233HKauAVL( )
        {
            root = null;
        }

        /**
         * Insert into the tree
         * @param x the item to insert.
         */
        public void insert( AnyType x )
        {
            root = insert( x, root );
        }

        /**
         * Remove from the tree. Nothing is done if x is not found.
         * @param x the item to remove.
         */
        private void remove( AnyType x )
        {
            remove( x, root );
        }


        /**
         * Internal method to remove from a subtree.
         * @param x the item to remove.
         * @param t the node that roots the subtree.
         * @return true if successful or false if not.
         */
        private boolean remove( AnyType x, A1233HKauAvlNode<AnyType> t )
        {
            while( t != null )
            {
                int compareResult = x.compareTo( t.element );

                if( compareResult < 0 )
                    t = t.left;
                else if( compareResult > 0 )
                    t = t.right;
                else
                {
                    if( t.status )
                    {
                        t.status = false;
                        return true;
                    }
                    else
                        while( !t.status )
                            t = t.right;
                    t.status = false;
                    return true;
                }
            }

            return false;
        }

        /**
        * Insert into the tree
        * @param x is the item in
         * which we remove all instances
        */
        public void removeAll( AnyType x )
        {
            removeAll( x , root );
        }

        /**
         * Find the smallest item in the tree.
         * @return smallest item or null if empty.
         */
        public AnyType findMin( )
        {
            if( isEmpty( ) )
                throw new UnderflowException("ERROR");
            return findMin( root ).element;
        }

        /**
         * Find the largest item in the tree.
         * @return the largest item of null if empty.
         */
        public AnyType findMax( )
        {
            if( isEmpty( ) )
                throw new UnderflowException("ERROR");
            return findMax( root ).element;
        }

        /**
         * Find an item in the tree.
         * @param x the item to search for.
         * @return true if x is found.
         */
        public boolean contains( AnyType x )
        {
            return contains( x, root );
        }

       /**
        * Find all x in tree
        * @param x the item to search for.
        * @return linked list with items found
        */
        public LinkedList<A1233HKauAvlNode<AnyType>> findAll(AnyType x )
        {
            LinkedList<A1233HKauAvlNode<AnyType>> linkList = new LinkedList<>();
            LinkedListIterator<A1233HKauAvlNode<AnyType>> theItr = linkList.zeroth( );;
            findAll( x, root, linkList , theItr );
            return linkList;
        }

        /**
         * Make the tree logically empty.
         */
        public void makeEmpty( )
        {
            root = null;
        }

        /**
         * Test if the tree is logically empty.
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty( )
        {
            return root == null;
        }

        /**
         * Print the tree contents in sorted active order.
         */
        public void printTree( )
        {
            if( isEmpty( ) )
                System.out.println( "Tree is currently empty" );
            else
                printTree( root );
        }

        /**
         * Prints out items and there left and right
         * nodes, height, and balance.
         * @param tof is boolean allowing a choice of
         * ascending or descending order
         */
        public void printBalTree( boolean tof )
        {
            if( isEmpty( ) )
                System.out.println( "Tree is currently empty" );
            else
                printBalTree( root, tof );
        }

        /**
        * Prints out items to a file and there left and right
        * nodes, height, and balance.
        * @param tof is boolean allowing a choice of
        * ascending or descending order
        */

        public void writeBalTree( boolean tof )
        {
            if( isEmpty( ) )
                System.out.println( "Tree is currently empty" );
            else
                writeBalTree( root, tof );
        }


        private static final int ALLOWED_IMBALANCE = 1;

        // Assume t is either balanced or within one of being balanced
        private A1233HKauAvlNode<AnyType> balance(A1233HKauAvlNode<AnyType> t )
        {
            if( t == null )
                return t;

            if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
                if( height( t.left.left ) >= height( t.left.right ) )
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
            else
            if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
                if( height( t.right.right ) >= height( t.right.left ) )
                    t = rotateWithRightChild( t );
                else
                    t = doubleWithRightChild( t );

            t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
            return t;
        }

        public void checkBalance( )
        {
            checkBalance( root );
        }

        private int checkBalance( A1233HKauAvlNode<AnyType> t ) {
            if (t == null)
                return -1;

            if (t != null) {
                int hl = checkBalance(t.left);
                int hr = checkBalance(t.right);
                if (Math.abs(height(t.left) - height(t.right)) > 1 ||
                        height(t.left) != hl || height(t.right) != hr)
                    System.out.println("OOPS!!");
            }

            return height(t);
        }

        /**
         * Internal method to insert into a subtree.
         * @param x the item to insert.
         * @param t the node that roots the subtree.
         * @return the new root of the subtree.
         */
        private A1233HKauAvlNode<AnyType> insert(AnyType x, A1233HKauAvlNode<AnyType> t )
        {
            if( t == null )
                return new A1233HKauAvlNode<>( x, null, null );

            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                t.left = insert( x, t.left );
            else if( compareResult > 0 )
                t.right = insert( x, t.right );
            else
                t.right = insert( x, t.right );
            return balance( t );
        }


        /**
         * Internal method to find the smallest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the smallest item.
         */
        private A1233HKauAvlNode<AnyType> findMin(A1233HKauAvlNode<AnyType> t )
        {
            if( t == null )
                return t;

            while( t.left != null )
                t = t.left;
            return t;
        }

        /**
         * Internal method to find the largest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the largest item.
         */
        private A1233HKauAvlNode<AnyType> findMax(A1233HKauAvlNode<AnyType> t )
        {
            if( t == null )
                return t;

            while( t.right != null )
                t = t.right;
            return t;
        }

        /**
         * Internal method to find an item in a subtree.
         * @param x is item to search for.
         * @param t the node that roots the tree.
         * @return true if x is found in subtree.
         */
        private boolean contains( AnyType x, A1233HKauAvlNode<AnyType> t )
        {
            while( t != null )
            {
                int compareResult = x.compareTo( t.element );

                if( compareResult < 0 )
                    t = t.left;
                else if( compareResult > 0 )
                    t = t.right;
                else
                    return true;    // Match
            }

            return false;   // No match
        }

        /**
        * Internal method to remove all items of same value in tree
        * @param x is item to search for.
        * @param t the node that roots the tree.
        */

        private void removeAll(AnyType x, A1233HKauAvlNode<AnyType> t )
        {
            if( t == null)
                return;

            int compareResult = x.compareTo( t.element );

            removeAll( x , t.left );

            removeAll( x, t.right );

            if( t.element != null && compareResult == 0)
                t.status = false;
        }

        /**
        * Internal method to find all items of same value in tree
        * @param x is item to search for.
        * @param t the node that roots the tree.
         * @param linkList is the returned linked list
         * @param theItr is an iterator for the list
        */

        private void findAll(AnyType x, A1233HKauAvlNode<AnyType> t , LinkedList<A1233HKauAvlNode<AnyType>> linkList,
                             LinkedListIterator<A1233HKauAvlNode<AnyType>> theItr) {
            if (t == null)
                return;

            int compareResult = x.compareTo(t.element);

            findAll(x, t.left, linkList, theItr);

            findAll(x, t.right, linkList, theItr);

            if (t.element != null && compareResult == 0)
            {
                linkList.insert( t , theItr );
                System.out.println(linkList);
                theItr.advance( );
            }
        }


        /**
         * Internal method to print a subtree in sorted order.
         * @param t the node that roots the tree.
         */
        private void printTree( A1233HKauAvlNode<AnyType> t )
        {
            if( t != null )
            {
                printTree( t.left );
                if( t.status )
                    System.out.println( t.element );
                printTree( t.right );
            }
        }

        /**
         * Internal method to print tree in ascending or desceing
         * order.
         * @param t the node that roots the tree.
         * @param tof is boolean and decides order
         */
        private void printBalTree(A1233HKauAvlNode<AnyType> t , boolean tof )
        {


            if( t != null && tof)
            {
                printBalTree( t.left , true);
                System.out.println( "Data: " + t.element + "\tHeight:  " + t.height
                                        + "\tBalance:  " + returnBalance(t) + " \tStatus:  " + returnStatus(t));
                System.out.println("\t\tLeft:\t" + printLChild(t.left));
                System.out.println("\t\tRight:\t" + printLChild(t.right));
                printBalTree( t.right , true);
            }
            else if ( t != null )
            {
                printBalTree( t.right , false);
                System.out.println( "Data: " + t.element + "\tHeight:  " + t.height
                                        + "\tBalance:  " + returnBalance(t) + " \tStatus:  " + returnStatus(t));
                System.out.println("\t\tLeft:\t" + printLChild(t.left));
                System.out.println("\t\tRight:\t" + printLChild(t.right));
                printBalTree( t.left , false);
            }
        }

        /**
         * Internal method to print tree in ascending or desceing
         * order to a file
         * @param t the node that roots the tree.
         * @param tof is boolean and decides order
         */

        public void writeBalTree(A1233HKauAvlNode<AnyType> t, boolean tof)
        {
            FileOutputStream outputStream = null;
            PrintWriter printWriter = null;

            try
            {

                outputStream = new FileOutputStream("A1233HKauAVLout.txt");
                printWriter = new PrintWriter(outputStream);

                write(t, printWriter, tof);

                printWriter.flush();

            }catch(IOException e)
            {
                System.out.println("An error occured");
                printWriter.close();
            }

        }

        /**
         * Internal method that is recursion for writeBalTree
         * @param t the node that roots the tree.
         * @param w is writer that writes to file.
         * @param tof is boolean deciding order.
         */

        private void write(A1233HKauAvlNode<AnyType> t, PrintWriter w, boolean tof) {
            if (t != null && tof) {
                write(t.left, w, true);
                w.println("Data:" + t.element + "\tHeight: " + t.height
                        + "\tBalance:  " + returnBalance(t) + " \tStatus:  " + returnStatus(t));
                w.println("\tLeft:    " + printLChild(t.left));
                w.println("\tRight:   " + printLChild(t.right));
                write(t.right, w, true);
            }
            else if (t != null)
            {
                write(t.right, w, false);
                w.println("Data:" + t.element + "\tHeight: " + t.height
                        + "\tBalance:  " + returnBalance(t) + " \tStatus:  " + returnStatus(t));
                w.println("\tLeft:    " + printLChild(t.left));
                w.println("\tRight:   " + printLChild(t.right));
                write(t.left, w, false);
            }
        }

        /**
         * Returns balance of a node to writing methods
         * @param t the node that roots the tree.
         */

        private String returnBalance( A1233HKauAvlNode<AnyType> t)
        {
            int leftHeight = -1 , rightHeight = -1;

            if( t.left == null)
                leftHeight = -2;
            else
                leftHeight = t.left.height;
            if( t.right == null )
                rightHeight = -2;
            else
                rightHeight = t.right.height;
            if((leftHeight == rightHeight))
                return "0";
            else if( leftHeight > rightHeight )
                return "-1";
            else
                return "1";
        }

        /**
         * Returns status of a node to writing methods
         * @param t the node that roots the tree.
         */

        private String returnStatus( A1233HKauAvlNode<AnyType> t)
        {
            if( t.status)
                return "Active";
            else
                return "Inactive";
        }

        /**
         * Returns left and right node to writing methods
         * @param t the node that roots the tree.
         */

        private String printLChild(A1233HKauAvlNode<AnyType> t )
        {
            if( t == null )
                return "null";
            else
                return "Data: " + t.element + "\tHeight: " + t.height + "\tBalance: " + returnBalance(t);
        }

        /**
         * Computes the mode of the avl tree
         * @return new instance of A1233HKauGetMode
         */

        public Result<AnyType> findMode()
        {
            int maxCount = 0;
            A1233HKauAvlNode<AnyType> maxValue = root;
            ArrayList<A1233HKauNumCount<AnyType>> items = new ArrayList<>();

            calculateMode( root, items );

            for( A1233HKauNumCount<AnyType> x : items )
            {
                if( x.numOfItems > maxCount )
                {
                    maxValue = x.getItem();
                    maxCount = x.numOfItems;
                }
        }

            return new A1233HKauGetMode(maxValue, maxCount);
        }

        /**
         * Computes the mode with recursion fo avl tree
         * @param t is the root of the nodes
         * @param items is the Arraylist of items
         */

        private void calculateMode(A1233HKauAvlNode<AnyType> t , ArrayList<A1233HKauNumCount<AnyType>> items)
        {

            if (t != null )
            {
                calculateMode(t.left , items );

                if(items.isEmpty() && t.status )
                {
                    items.add(new A1233HKauNumCount<>(t));
                    return;
                }
                else
                    for( A1233HKauNumCount<AnyType> x : items )
                        if( x.item.element.compareTo(t.element) == 0 && x.item.status )
                        {
                            x.inc();
                            return;
                        }
                    if( t.status )
                        items.add( new A1233HKauNumCount<>(t));
                calculateMode(t.right, items );
            }
        }

        /**
         * Hold each item with its number of items in the tree
         */

        private class A1233HKauNumCount<AnyType>
        {
            public A1233HKauNumCount(A1233HKauAvlNode<AnyType> t )
            {
                item = t;
                numOfItems = 1;
            }

            public A1233HKauAvlNode<AnyType> getItem()
            {
                return item;
            }

            public void inc()
            {
                numOfItems++;
            }

            public A1233HKauAvlNode<AnyType> item;
            public int numOfItems;
        }

        /**
         * This is the container for the mode.
         * Allows you to print and hold values.
         */

        private static class A1233HKauGetMode<AnyType> implements Result
        {
            private AnyType mode;
            private int count = 0;

            @Override
            public String toString() {
                return "mode=" + mode +
                        ", count=" + count +
                        '}';
            }

            public A1233HKauGetMode(AnyType currentMode, int currentCount )
            {
                mode = currentMode;
                count = currentCount;
            }

            @Override
            public AnyType mode( ) {
                return mode;
            }

            @Override
            public int count( ) {
                return count;
            }
        }


        /**
         * Return the height of node t, or -1, if null.
         */
        private int height( A1233HKauAvlNode<AnyType> t )
        {
            return t == null ? -1 : t.height;
        }

        /**
         * Rotate binary tree node with left child.
         * For AVL trees, this is a single rotation for case 1.
         * Update heights, then return new root.
         */
        private A1233HKauAvlNode<AnyType> rotateWithLeftChild(A1233HKauAvlNode<AnyType> k2 )
        {
            A1233HKauAvlNode<AnyType> k1 = k2.left;
            k2.left = k1.right;
            k1.right = k2;
            k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
            k1.height = Math.max( height( k1.left ), k2.height ) + 1;
            return k1;
        }

        /**
         * Rotate binary tree node with right child.
         * For AVL trees, this is a single rotation for case 4.
         * Update heights, then return new root.
         */
        private A1233HKauAvlNode<AnyType> rotateWithRightChild(A1233HKauAvlNode<AnyType> k1 )
        {
            A1233HKauAvlNode<AnyType> k2 = k1.right;
            k1.right = k2.left;
            k2.left = k1;
            k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
            k2.height = Math.max( height( k2.right ), k1.height ) + 1;
            return k2;
        }

        /**
         * Double rotate binary tree node: first left child
         * with its right child; then node k3 with new left child.
         * For AVL trees, this is a double rotation for case 2.
         * Update heights, then return new root.
         */
        private A1233HKauAvlNode<AnyType> doubleWithLeftChild(A1233HKauAvlNode<AnyType> k3 )
        {
            k3.left = rotateWithRightChild( k3.left );
            return rotateWithLeftChild( k3 );
        }

        /**
         * Double rotate binary tree node: first right child
         * with its left child; then node k1 with new right child.
         * For AVL trees, this is a double rotation for case 3.
         * Update heights, then return new root.
         */
        private A1233HKauAvlNode<AnyType> doubleWithRightChild(A1233HKauAvlNode<AnyType> k1 )
        {
            k1.right = rotateWithLeftChild( k1.right );
            return rotateWithRightChild( k1 );
        }

        private static class A1233HKauAvlNode<AnyType> implements Comparable<A1233HKauAvlNode> {
            // Constructors
            A1233HKauAvlNode(AnyType theElement )
            {
                this( theElement, null, null );
            }

            A1233HKauAvlNode(AnyType theElement, A1233HKauAvlNode<AnyType> lt, A1233HKauAvlNode<AnyType> rt )
            {
                element  = theElement;
                left     = lt;
                right    = rt;
                height   = 0;
                status   = true;
            }

            AnyType                   element;      // The data in the node
            A1233HKauAvlNode<AnyType> left;         // Left child
            A1233HKauAvlNode<AnyType> right;        // Right child
            int                       height;       // Height
            boolean                   status;       // Active or Inactive

            @Override
            public int compareTo(A1233HKauAvlNode t) {
                if( this.height - t.height > 0 )
                    return 1;
                else if( this.height - t.height < 0 )
                    return -1;
                else
                    if( this.right.height - t.right.height > 0 )
                        return 1;
                    else if( this.right.height - t.right.height < 0 )
                        return -1;
                    else
                        return Integer.compare(this.left.height - t.left.height, 0);
            }

            @Override
            public String toString() {
                return "AvlNode: " +
                        "element=" + element +
                        ", height=" + height +
                        ", status=" + status + "\n";
            }
        }

        /** The tree root. */
        private A1233HKauAvlNode<AnyType> root;

    }


