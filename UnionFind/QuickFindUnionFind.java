public class QuickFindUnionFind
{
    private int[] id;
    
    public QuickFindUnionFind(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;  // Set id of each object to itself         
    }

    public boolean isConnected(int p, int q)    // Check whether p and q are in the same component
    {   
        return id[p] == id[q];   
    }

    public void union(int p, int q)
    {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++)
        {
            if(id[i] == pid) id[i] = qid;   // Change all entries with id[p] to id[q]
        }
    }
}
