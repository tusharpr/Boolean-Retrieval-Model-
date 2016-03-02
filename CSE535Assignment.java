


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class CSE535Assignment { 
 static String[] answer = new String[500];
 public static String args0,args1;
    
    public static void main(String[] args) throws IOException {
        args1=args[1];
        args0=args[0];
        idx_file_processor fr = new idx_file_processor();
        
        ReadFile ob= new ReadFile(args[3]);
        String[] s= ob.fileopen();
        fr.store();
             
        int i=2;
        answer[0]="FUNCTION: getTopK "+args[2];
        answer[1]="Result: "+fr.getTopK(Integer.parseInt(args[2]));
        for(int line=0;line<s.length;line++)
        {
        fr.init_counters();
        String[] terms = s[line].split(" ");
        HashMap<String,Integer> temphashmap = new HashMap<>();
        int itr=0;
        for(int t=0;t<terms.length;t++)
        {
            if(fr.get_termpost().get(terms[t])==null)
            {
                continue;
            }
            temphashmap.put(terms[itr], fr.get_termpost().get(terms[t]));
            itr++;
        }
        String sorted_terms= fr.sorted_terms(temphashmap);
        String[] sortedd_terms;
        
        sortedd_terms = sorted_terms.split(" ");
        String terms_coma="";
        for(int j=0;j<terms.length;j=j+1)
        {
            terms_coma+=", "+terms[j];
        }
        for(int j=0;j<terms.length;j++)
        {
 
            answer[i]="FUNCTION: getPostings "+terms[j];
            answer[1+i]="Ordered by doc IDs: "+fr.getPosting(terms[j]);
            answer[2+i]="Ordered by TF: "+fr.getPosting2(terms[j]);
            i=i+3;
        }
        
        LinkedList<Integer> tempresult = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<Integer> tempresult_opt = new LinkedList<>();
        
        double st = System.nanoTime();
        tempresult=null;
        for(int in=0;tempresult==null;in++)
        {
        tempresult=fr.getLLf(terms[in]);
        }
        for(int j=1;j<terms.length ;j++)
        {
            if(fr.getLLf(terms[j])==null)
            {
            continue;
            }
            tempresult=fr.TAATand(tempresult,fr.getLLf(terms[j]));
        }
        double f = System.nanoTime();
        double time=(f-st)/1000000000;
        int a=fr.get_taatandcount();
        
        fr.init_countersa();
        tempresult_opt=null;
          for(int in=0;tempresult_opt==null;in++)
        {
            tempresult_opt=fr.getLLf(sortedd_terms[in]);
        }
        for(int j=1;j<sortedd_terms.length;j++)
        {
            if(fr.getLLf(sortedd_terms[j])==null)
            {
                continue;
            }
            tempresult_opt=fr.TAATand(tempresult_opt,fr.getLLf(sortedd_terms[j]));
        }
        
        answer[i]="FUNCTION: termAtATimeQueryAnd "+terms_coma.substring(2);
        answer[i+1]=tempresult.size()+" documents are found"; 
        answer[i+2]=a+" comparisions are made";
        answer[i+3]=String.format("%.10f",time)+" seconds are used";
        answer[i+4]=fr.get_taatandcount()+" comparisons are made with optimization (optional bonus part)";
        if(tempresult.size()!=0)
        {
        answer[i+5]="Result: "+Arrays.toString(fr.sortll(tempresult)).substring(1,tempresult.toString().length()-1);
        }
        else
        {
            answer[i+5]="Result: terms not found";
        }
        i=i+6;
        
        st = System.nanoTime();
        tempresult=null;
for(int in=0;tempresult==null;in++)
        {
        tempresult=fr.getLLf(terms[in]);
        }
        for(int j=1;j<terms.length ;j++)
        {
            if(fr.getLLf(terms[j])==null)
            {
            continue;
            }
            tempresult=fr.TAAToR(tempresult,fr.getLLf(terms[j]));
        }
         f = System.nanoTime();
         time=(f-st)/1000000000;
         
        int b=fr.get_taatorcount();
        fr.init_countersb();
        tempresult_opt=null;
          for(int in=0;tempresult_opt==null;in++)
        {
            tempresult_opt=fr.getLLf(sortedd_terms[in]);
        }
        for(int j=1;j<sortedd_terms.length;j++)
        {
            if(fr.getLLf(sortedd_terms[j])==null)
            {
                continue;
            }
            tempresult_opt=fr.TAAToR(tempresult_opt,fr.getLLf(sortedd_terms[j]));
        }
        answer[i]="FUNCTION: termAtATimeQueryOr "+terms_coma.substring(2);
        answer[i+1]=tempresult.size()+" documents are found"; 
        answer[i+2]=b+" comparisions are made";
        answer[i+3]=String.format("%.10f",time)+" seconds are used";
        answer[i+4]=fr.get_taatorcount()+" comparisons are made with optimization (optional bonus part)";
        if(tempresult.size()!=0)
        {
        answer[i+5]="Result: "+Arrays.toString(fr.sortll(tempresult)).substring(1,tempresult.toString().length()-1);
                }
        else
        {
            answer[i+5]="Result: terms not found";
        }
        i=i+6;
        
        st = System.nanoTime();
        result =fr.DAATand(s[line]); 
        f = System.nanoTime();
        time=(f-st)/1000000000;
        answer[i]="FUNCTION: docAtATimeQueryAnd "+terms_coma.substring(2);
        answer[i+1]=result.size()+" documents are found"; 
        answer[i+2]=fr.get_daatandcount()+" comparisions are made";
        answer[i+3]=String.format("%.10f",time)+" seconds are used";
        if(result.size()!=0)
        {
        answer[i+4]="Result: "+result.toString().substring(1,result.toString().length()-1);
        }
        else
        {
          answer[i+4]="Result: terms not found";
        }
        i=i+5;
        st = System.nanoTime();
        result =fr.DAATor(s[line]);
        f = System.nanoTime();
        time=(f-st)/1000000000;
        answer[i]="FUNCTION: docAtATimeQueryOr "+terms_coma.substring(2);
        answer[i+1]=result.size()+" documents are found"; 
        answer[i+2]=fr.get_daatorcount()+" comparisions are made";
        answer[i+3]=String.format("%.10f",time)+" seconds are used";
        if(result.size()!=0)
        {
        answer[i+4]="Result: "+result.toString().substring(1,result.toString().length()-1);
        }
        else
        {
          answer[i+4]="Result: terms not found";
        }
        i=i+5;
        }
        createlog();
        
    }

public static void createlog() throws IOException {
    
     try (PrintWriter pw = new PrintWriter(new FileWriter(args1))) {
         for (String p: answer) {
             if(p!=null)
             {
             pw.write(p+"\n");
             }
         }
     }
}    
}

class idx_file_processor {
    
    String line="";
    int post;
    int f;
    String name="";
    boolean[] pointchecker;
    static int[] point;
    static int[] size;
    static int[] point2;
    static int[] size2;
    LinkedList<Integer>[] pl2;
    
    public static int taatandcount;
    public static int taatorcount;
    public static int daatandcount;
    public static int daatorcount;

    HashMap termpost = new HashMap();
    HashMap termposttemp = new HashMap();
    HashMap<Integer,Integer> df = new HashMap();
    
    HashMap<String,LinkedList<Integer>> termdocids = new HashMap(); 
    HashMap<String,LinkedList<Integer>> termdocidsfreq = new HashMap();
    public  void store() throws IOException
    {
        
        String path1=CSE535Assignment.args0;//F:/test.idx
        ReadFile ob= new ReadFile(path1.toString());
        String[] aryline= ob.fileopen();
    
    for(int k=0;k<aryline.length;k++)
    {
    f=0;
    post=0;
    LinkedList<Integer> docids = new LinkedList<>();
    String[] parts = aryline[k].split("\\\\");
    String part1 = parts[0];
    name=part1;
    String part2 = parts[1];
    post = Integer.parseInt(part2.substring(1));
    String part3 = parts[2].substring(2,parts[2].length()-1);
    String[] parts2 = part3.split(", ");
    for(int j=0;j<post;j++)
    {
        String[] parts3 = parts2[j].split("/");
        docids.add(Integer.parseInt(parts3[0]));
        df.put(Integer.parseInt(parts3[0]),Integer.parseInt(parts3[1]));
        f+=Integer.parseInt(parts3[1]);
    }
    
    termpost.put(name,post);
    termdocids.put(name, docids);
    termdocidsfreq.put(name, sortbyfreq());
    }      
  }
  
    
  public String getTopK(int n)
  {
      
      String topK="";
      termposttemp = (HashMap) termpost.clone();
      for(int i=0;i<n;i++)
      {
        topK=topK+getmaxKey(termposttemp)+", ";  
      }
      return topK.substring(0,topK.length()-2);
      }
  
    public String sorted_terms(HashMap<String,Integer> hmap)
  {
      
      String sortedterm="";
      HashMap<String,Integer> temp = (HashMap<String,Integer>) hmap.clone();

      for(int i=0;i<hmap.size();i++)
      {
      Map.Entry<String,Integer> entry=temp.entrySet().iterator().next();
      int min=entry.getValue();
        sortedterm=sortedterm+getminKey(temp,min)+" ";  
      }
      return sortedterm.substring(0,sortedterm.length()-1);
      }
      
  public String getmaxKey(Map mp) {
    int max=0;
    String key="";
    
    Iterator it = termposttemp.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        if((int) pair.getValue()>max)
        {
        max = (int) pair.getValue();
        key = (String) pair.getKey();
        }
    }
    mp.remove(key);
    return key;
}
  public String getminKey(Map<String,Integer> mp,int min) {
    String key="";

    Iterator it = mp.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        if((int) pair.getValue()<=min)
        {
        min = (int) pair.getValue();
        key = (String) pair.getKey();
        }
    }
    mp.remove(key);
    return key;
}
  
  public int getmaxKey2(Map mp) {
    int max=0;
    int key=0;
    Iterator it = mp.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
          if((int) pair.getValue()>max)
        {
        max = (int) pair.getValue();
        key = (int) pair.getKey();
          }
        
    }
    mp.remove(key);
    return key;
}


  public String getPosting(String s)
  {
      
      if(!termdocids.containsKey(s))
      {
          return "term not found";
      }
      LinkedList<Integer> docidstemp2 =  new LinkedList<>();
      String docidlist="";
      docidstemp2 =(LinkedList)termdocids.get(s);

        for (int st : docidstemp2) {
            docidlist+=st+", ";
        }
      return docidlist.substring(0,docidlist.length()-2);
}
    public String getPosting2(String s) {
            if(!termdocidsfreq.containsKey(s))
      {
          return "term not found";
      }  
      LinkedList<Integer> docidstemp2 =  new LinkedList<>();
      String docidlist="";
      
      docidstemp2 =(LinkedList)termdocidsfreq.get(s);
        for (int st : docidstemp2) {
            docidlist+=st+", ";
        }
      return docidlist.substring(0,docidlist.length()-2);
}
 public LinkedList<Integer> sortbyfreq()
  { 
      LinkedList<Integer> sorted = new LinkedList<>();
      for(int q=0;q<post;q++)
      {
      sorted.add(getmaxKey2(df));
      }
      return sorted ;
      }
 public LinkedList<Integer> TAAToR(LinkedList<Integer> ll1,LinkedList<Integer> ll2)
 {
     LinkedList<Integer> templl = new LinkedList<>();
     int i;
     templl=(LinkedList) ll1.clone();
     for(int j=0;j<ll2.size();j++)
     {
         for(i=0;i<ll1.size();i++)
         {
             ++taatorcount;
            if(Objects.equals(ll2.get(j), ll1.get(i)))
            {
                break;
            }
          }
         if(i==(ll1.size()))
                 {
                     templl.add(ll2.get(j));
                 }
     }
     return templl;
 }
 public LinkedList<Integer> getLL(String s)
 {
     if(termdocids.containsKey(s))
     {
     return (LinkedList<Integer>)termdocids.get(s).clone();
     }
     else
     {
         return null;
     }
 }
  public LinkedList<Integer> getLLf(String s)
 {
     if(termdocidsfreq.containsKey(s))
     {
     return (LinkedList<Integer>)termdocidsfreq.get(s).clone();
     }
     else{
         return null;
     }
 }
 public LinkedList<Integer> TAATand(LinkedList<Integer> ll1,LinkedList<Integer> ll2)
 {
     int i=0,j=0;
     LinkedList<Integer> templl2 = new LinkedList<>();
     
     for(j=0;j<ll2.size();j++)
     {
         for(i=0;i<ll1.size();i++)
         {
             ++taatandcount;
            if(Objects.equals(ll2.get(j), ll1.get(i)))
            {
                
                break;
            }
          }
         if(i!=(ll1.size()))
                 {
                     templl2.add(ll2.get(j));
                 }
     }

     return templl2;
     
 }
 public LinkedList<Integer> DAATand(String s)
 {
         String[] term = s.split(" ");
         
         point = new int[term.length];
         size = new int[term.length];
         int[] docidaray = new int[term.length];
         int tempmax;
         LinkedList<Integer>[] pl = new LinkedList[term.length];
         LinkedList<Integer> result = new LinkedList<>();
         for(int i=0;i<term.length;i++)
         {
             point[i]=0;
         }
         
         for(int i=0;i<term.length;i++)
         {
             if(getLL(term[i])==null)
             {
                 continue;
             }
             pl[i]=(LinkedList<Integer>) (termdocids.get(term[i])).clone();
             size[i]=pl[i].size();
             
         }
         while(whilecond(term))
         {
            for(int i=0;i<pl.length;i++)
            {
                docidaray[i]=pl[i].get(point[i]);
            }
            if(araysame(docidaray))
            {
             result.add(docidaray[0]);
            for(int i=0;i<pl.length;i++)
            {
             ++point[i];
            }
            }
            else
            {
             tempmax=maxofarray(docidaray);
             boolean flag=true;
             for(int j=0;j<pl.length;j++)
             {
                 
                 if(docidaray[j]==tempmax && flag)
                 {
                     
                 flag=false;
                 }
                 else
                 {
                  ++daatandcount;   
                 }
                 
                 if(docidaray[j]<tempmax)
                 {
                     ++point[j];
                 }
             }
            }
         }
     return result;
 }
 public int maxofarray(int[] a)
 {
     int max=a[0];
     for(int i=1;i<a.length;i++)
     {
         ++daatandcount;
         if(a[i]>max)
         {
             max=a[i];
         }
     }
     
     return max;
 }

 public boolean whilecond(String[] s)
 {
    // System.out.print("-");
   for(int i=0;i<s.length;i++)
   {
       if(point[i] < size[i])
       { 
       }
       else
       {
           return false;
       }
   }
   return true;
 }
 
 public boolean whilecond2(String[] s)
 {
   for(int i=0;i<s.length;i++)
   {
       if(point2[i] < size2[i])
       { 
       }
       else
       {
           pointchecker[i]=false;
       }
   }
   return whilecond2return(pointchecker);
 }
 public boolean whilecond2return(boolean[] a)
 {
     boolean t=a[0];
     int i;
     for(i=0;i<a.length;i++)
     {
         if(a[i]==t)
         {
             t=a[i];
         }
         else
         {
             break;
         }
     }
     if(i!=a.length)
     {
         return true;
     }
     return a[0];
 }
  public boolean araysame(int[] a)
 {
    int same=a[0];
   for(int i=1;i<a.length;i++)
   {
       ++daatandcount;
       if(a[i]==same)
       {
           same=a[i];
       }
       else
       {
           return false;
       }
   }
   return true;
 }
  
  
   public LinkedList<Integer> DAATor(String s)
 {
         String[] term = s.split(" ");

         point2 = new int[term.length];
         size2 = new int[term.length];
         pointchecker=new boolean[term.length];
         int[] docidaray = new int[term.length];
         pl2 = new LinkedList[term.length];
         LinkedList<Integer> result = new LinkedList<>();
         for(int i=0;i<term.length;i++)
         {
             point2[i]=0;
         }

         for(int i=0;i<term.length;i++)
         {
          if(getLL(term[i])==null)
             {
                 continue;
             }

             pl2[i]=(LinkedList<Integer>) (termdocids.get(term[i])).clone();
             size2[i]=pl2[i].size();
         }
         for(int i=0;i<pl2.length;i++)
         {
             pointchecker[i]=true;
         }
         int m;
         while(whilecond2(term))
         {
            m=0;
            for(int i=0;i<pl2.length ;i++)
            {
                if(pointchecker[i])
                {
                docidaray[m]=pl2[i].get(point2[i]);
                m++;
                }
            }
             int k;
             for(int j=0;j<docidaray.length;j++)
             {
                     for(k=0;k<result.size();k++)
                     {
                     ++daatorcount;
                     if(docidaray[j]==result.get(k))
                     {            
                         break;
                     }
                     }
                     if(k==result.size())
                     {
                     result.add(docidaray[j]);
                     }
                     ++point2[j];
             }
          
            }
         
     return result;
 }
   
 public int[] sortll(LinkedList<Integer> ll)
 {
     int[] x= new int[ll.size()];
     int j=0;
     int temp;
     for(Object i:ll.toArray())
     {
         x[j]=(int)i;
         j++;
     }
     
     
             for (int i = 0; i < ll.size(); i++) 
        {
            for (int k = i + 1; k < ll.size(); k++) 
            {
                if (x[i] > x[k]) 
                {
                    temp = x[i];
                    x[i] = x[k];
                    x[k] = temp;
                }
            }
        }
     return x;
 }
   public void init_counters()
   {
        taatandcount=0;
        taatorcount=0;
        daatandcount=0;
        daatorcount=0;
   }
      public void init_countersa()
   {
        taatandcount=0;
        
   }
         public void init_countersb()
   {
        taatorcount=0;
        
   }
   public int get_taatorcount()
   {
       return  taatorcount;
   }
   public int get_taatandcount()
   {
       return  taatandcount;
   }
   public int get_daatandcount()
   {
       return  daatandcount;
   }
   public int get_daatorcount()
   {
       return  daatorcount;
   }
   public HashMap<String,Integer> get_termpost()
   {
       return (HashMap<String,Integer>) termpost.clone();
   }
        
}

class ReadFile {
    private final String path;
    int readlinen() throws IOException
        {
        FileReader fr1 = new FileReader(path);
        BufferedReader textreader1 = new  BufferedReader(fr1);
        String aLine1;   
        int nline=0;
        while(( aLine1=textreader1.readLine())!=null)
        {
            nline++;
        }
        textreader1.close();
        return nline;
        }
    public  ReadFile(String aPath)
    {
        path=aPath;
        
} 
    public String[] fileopen() throws IOException
    {
        
        FileReader fr = new FileReader(path);
        BufferedReader textreader = new  BufferedReader(fr);
        int line=readlinen();
        String[] read_line = new String[line];
        
        for(int i=0;i<line;i++)
        {
            read_line[i]=textreader.readLine();
        }
        textreader.close();
        return read_line;
    }
}