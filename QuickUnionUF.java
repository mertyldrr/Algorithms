public class QuickUnionUF
{
    private int[] id;

    public QuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;  // set id of each object to itself
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

        id[i] = j;
    }
}