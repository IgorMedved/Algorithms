package utility.sort.helper;

public class IndexPair
{
  private int mT;
  private int mQ;
  
  public IndexPair()
  {
  }
  
  public IndexPair (int q, int t)
  {
    setPair(q, t);
  }
  
  public void setPair (int q, int t)
  {
    setQ(q);
    setT(t);
  }
  
  public void setQ( int q)
  {
    mQ = q;
  }
  
  public void setT (int t)
  {
    mT = t;
  }
  
  public int getQ()
  {
    return mQ;
  }
  
  public int getT()
  {
    return mT;
  }
  
}