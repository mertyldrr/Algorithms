/*
    WEIGHTED QUICK UNION
    improved version of quick union by balancing the tree.
    use an size[] array to record the number of object in each subtree,
    when merge two subtree, shorter tree's root become the child of the 
    higher one.
*/
public class WeightedQuickUnionUF {
    private int[] id;
    private int[] size;

    public WeightedQuickUnionUF(int N)
    {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++)
        {
            id[i] = i;  // set id of each object to itself
            size[i] = 1; // at the start, every subtree has one object(itself)
        }   
    }

    private int root(int i)
    {
        while(i != id[i]) i = id[i];    // chase parent pointers until reach root
        return i;
    }

    public boolean isConnected(int p, int q)
    {
        return root(p) == root(q);  // check if p and q have same root
    }

    public void union(int p, int q) // change root of p to point to root of q
    {
        int i = root(p);
        int j = root(q);
        if(i==j) return;

        // smaller root should point to large one
        if(size[i] < size[j])
        {
            id[i] = j;
            size[j] += size[i];  // update the size of j
        }

        else
        {
            id[j] = i;
            size[i] += size[j]; // update the size of i
        }


        
    }
}