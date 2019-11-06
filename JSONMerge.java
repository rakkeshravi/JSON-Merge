import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;
import java.util.regex.Pattern;
 
public class JSONMerge 
{
    public static void mergeJSONfiles(String folderPath,String inputFileBaseName,String outputfilebase,int maxfilelength) throws Exception 
    {
        //Create a json parser and jsonarray
        JSONParser jsonParser = new JSONParser();
        JSONArray jarrfin=new JSONArray();
        File directory = new File(folderPath);
        String key="";

        //Iterate throught the files in folder
        for(File f : directory.listFiles())
        {
            String pattern = inputFileBaseName+"[1-9]+[.]json";
            boolean matches = Pattern.matches(pattern, f.getName());
            //find folders matching the input pattern
            if(matches)
            {
                try (FileReader reader = new FileReader(f.getName()))
                {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);
           
                    // typecasting obj to JSONObject 
                    JSONObject jo = (JSONObject) obj;
                
                    Set<String> keys=jo.keySet();
                    key=keys.iterator().next();
                 
                    JSONArray jarr=(JSONArray)jo.get(key);
                 
                    for(Object o : jarr)
                    {
                        if(o instanceof JSONObject)
                        {
                            jarrfin.add((JSONObject)o);
                        }
                    }
      
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }         
            }
        }
        
        JSONObject finobj=new JSONObject();
        
        int keylength=key.getBytes("UTF-8").length;
        if(keylength>maxfilelength)
        {
            System.out.println("Maxfilesize inadequate and needs to be increased");
            return;
        }
        int n=jarrfin.size();
        int i=0,s=0,outputfilecount=1;

        //convert json to string array for slicing purposes
        ArrayList<String> mylist = new ArrayList<String>();   
        int length = jarrfin.size();
        for (i=0;i<length;i++){ 
            mylist.add(jarrfin.get(i).toString());
        }
        i=0;
        //storing the merged files in output files
        //while more objects to be written
        while(i<n)
        {
            //initialising currlength to keylength
            int currlength=keylength;
            //accomodating objects in current file until it does not exceed maximum file size
            while(i<n && currlength+jarrfin.get(i).toString().getBytes("UTF-8").length < maxfilelength)
            {
                currlength+=jarrfin.get(i).toString().getBytes("UTF-8").length;
                i++;
            }
 
            //Case when individual record is greater than maxfilesize
            if(i<n && jarrfin.get(i).toString().getBytes("UTF-8").length > maxfilelength)
            {
                    System.out.println("Object size greater than maxfilesize.Maxfilesize is inadequate");
                    return;
            }
            //writing to current file and incrementing to next file
            try (FileWriter file = new FileWriter(""+outputfilebase+outputfilecount+".json")) {
                    finobj.put(key,mylist.subList(s,i));
                    file.write(finobj.toJSONString());
                    file.flush();
                    outputfilecount++;
                    s=i;
            } catch (IOException e) {
                e.printStackTrace();
            } 
        } 
    }

    //@SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        //calling mergeJSONfiles function
        mergeJSONfiles(".","data","output",100);
        
    }
}