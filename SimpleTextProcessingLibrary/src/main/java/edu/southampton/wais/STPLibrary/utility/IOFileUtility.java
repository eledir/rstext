/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.southampton.wais.STPLibrary.utility;

/**
 *
 * @author antonio
 */
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */







import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.dataStructure.AnnotationObject;
import edu.southampton.wais.STPLibrary.dataStructure.DoubleSingleNode;
import edu.southampton.wais.STPLibrary.dataStructure.Node;
import edu.southampton.wais.STPLibrary.file.TextFile;




/* Utils.java is used by FileChooserDemo2.java. */
public class IOFileUtility {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String owl= "owl";
    public final static String xml = "xml";

    private final static Logger LOGGER = Logger.getLogger(IOFileUtility.class
			.getName());
    
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = IOFileUtility.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

       public static HashSet<String> readHashSetStringFromFile(File f) {
        FileReader fileReader = null;
        try {
        	HashSet<String> list = new HashSet<String>();
            fileReader = new FileReader(f);
            BufferedReader preStopFile = new BufferedReader(fileReader);
            String stopLinePreOrder = preStopFile.readLine();
            while (stopLinePreOrder != null) {
                list.add(stopLinePreOrder.trim());
                stopLinePreOrder = preStopFile.readLine();
            }
            preStopFile.close();
            return list;

        } catch (IOException ex) {
           
        	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
            
        	return null;

        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
         
            	
            	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                
            	return null;
            }
        }



    }

   
       
       public static Set<String> readSetStringFromFile(File f) {
           FileReader fileReader = null;
           try {
           	Set<String> list = new HashSet<String>();
               fileReader = new FileReader(f);
               BufferedReader preStopFile = new BufferedReader(fileReader);
               String stopLinePreOrder = preStopFile.readLine();
               while (stopLinePreOrder != null) {
                   list.add(stopLinePreOrder.trim());
                   stopLinePreOrder = preStopFile.readLine();
               }
               preStopFile.close();
               return list;

           } catch (IOException ex) {
              
           	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
               
           	return null;

           } finally {
               try {
                   fileReader.close();
               } catch (IOException ex) {
            
               	
               	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                   
               	return null;
               }
           }



       }
       
       
       
       
       public static ArrayList<DoubleSingleNode> readListSingleFromFile(File f, String re) {
           FileReader fileReader = null;
           try {
           	ArrayList<DoubleSingleNode> list = new ArrayList<DoubleSingleNode>();
               fileReader = new FileReader(f);
               BufferedReader preStopFile = new BufferedReader(fileReader);
               String stopLinePreOrder = preStopFile.readLine();
               while (stopLinePreOrder != null) {
                   
            	   
            	   String[] s =stopLinePreOrder.split(re);
            	   
            	   Double d=new Double(s[0]);
            	   DoubleSingleNode node = new DoubleSingleNode(s[1],d);
            	   list.add(node);
                   stopLinePreOrder = preStopFile.readLine();
                   
               }
               preStopFile.close();
               return list;

           } catch (IOException ex) {
              
           	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
               
           	return null;

           } finally {
               try {
                   fileReader.close();
               } catch (IOException ex) {
            
               	
               	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                   
               	return null;
               }
           }



       }

       public static ArrayList<String> readListStringFromFile(File f) {
           FileReader fileReader = null;
           try {
           	ArrayList<String> list = new ArrayList<String>();
               fileReader = new FileReader(f);
               BufferedReader preStopFile = new BufferedReader(fileReader);
               String stopLinePreOrder = preStopFile.readLine();
               while (stopLinePreOrder != null) {
                   list.add(stopLinePreOrder.trim());
                   stopLinePreOrder = preStopFile.readLine();
               }
               preStopFile.close();
               return list;

           } catch (IOException ex) {
              
           	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
               
           	return null;

           } finally {
               try {
                   fileReader.close();
               } catch (IOException ex) {
            
               	
               	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                   
               	return null;
               }
           }



       }

       
       

       public static ArrayList<String> readBufferReaderFromFile(File f,String regEx) {
           FileReader fileReader = null;
           try {
           	ArrayList<String> list = new ArrayList<String>();
               fileReader = new FileReader(f);
               BufferedReader preStopFile = new BufferedReader(fileReader);
               String stopLinePreOrder = preStopFile.readLine();
               while (stopLinePreOrder != null) {
            	   
            	  String [] strings= stopLinePreOrder.split(regEx);
                  for (int i = 0; i < strings.length; i++) {
                	  list.add(strings[i].toLowerCase().trim());
                      
				} 
            	  
            	  stopLinePreOrder = preStopFile.readLine();
               }
               preStopFile.close();
               return list;

           } catch (IOException ex) {
              
           	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
               
           	return null;

           } finally {
               try {
                   fileReader.close();
               } catch (IOException ex) {
            
               	
               	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                   
               	return null;
               }
           }



       }

       
   

       public static String readBufferReaderFromFileToString(File f) {
           FileReader fileReader = null;
           try {
           	    StringBuilder builder=  new StringBuilder();
               fileReader = new FileReader(f);
               BufferedReader preStopFile = new BufferedReader(fileReader);
               String stopLinePreOrder = preStopFile.readLine();
               while (stopLinePreOrder != null) {
            	   
            	 	  builder.append(stopLinePreOrder.toLowerCase().trim());
                	  builder.append(" ");
                	  stopLinePreOrder = preStopFile.readLine();
				
            	  
            	  
               }
               
               return builder.toString();

           } catch (IOException ex) {
              
           	LOGGER.severe("Not file "+ f.getPath()+ " "+ex.toString());
               
           	return null;

           } finally {
               try {
                   fileReader.close();
               } catch (IOException ex) {
            
               	
               	LOGGER.severe("closing file "+ f.getPath()+ " "+ex.toString());
                   
               	return null;
               }
           }



       }

       
   
   




    public static void createRandomIndexAccess(List<Integer> listAdd, int step, int size){


    if (step < size) {

            int index;

            Random random = new Random();

            int[] indexEqua = new int[step];

            while (step > 0) {

                index = random.nextInt(size);

                             //verico che non ci sono elmenti uguali
                    if (Arrays.binarySearch(indexEqua, index) < 0) {

                        listAdd.add(index);
                        indexEqua[-step + step] = index;
                        Arrays.sort(indexEqua);
                        step--;

                    }

                }




        } else {
            System.err.println("Error step and size could not be the same");
        }

    }

    public static void createRandomIndexAccess(List<Integer> listAdd, List<Integer> listSub, int dimList, int interval) {


        //verifica i vincoli tra step e size e inoltre faccio in modo che i numeri che posso insererire nel
        //nuovo vettore sono maggiori di quelli che non sono stati messi nella lista da sottrarre
        if (dimList < interval && (interval-listSub.size())>dimList) {

            int index;

            Random random = new Random();

            int[] indexEqua = new int[dimList];

            boolean wait = true;




            while (dimList > 0) {

                index = random.nextInt(interval);



                // System.out.println(step);



                //modalit� training non c'� list sub
                if (listSub != null) {




                    //verifico che non ci sono elementi uguali e che non siano prensenti nella lista
                    if ((Collections.binarySearch(listSub, index) < 0) && (Arrays.binarySearch(indexEqua, index) < 0)) {
                        listAdd.add(index);
                        indexEqua[-dimList + dimList] = index;
                        Arrays.sort(indexEqua);
                        dimList--;



                    }
                } else {

                  System.err.println("list sub null");
                  break;
                }


            }

        } else {
            System.err.println("Error step and size could not be the same or list sub contatins to much values");
        }
    }

    public static List<Integer> mergeList(List<Integer> list1, List<Integer> list2) {

             list1.addAll(list2);
             Collections.sort(list1);
             return list1;
    }

    public static List<Integer> readListIntegerFromFile(File f,boolean debug) {
        FileReader fileReader = null;
        try {
            List<Integer> list = new ArrayList<Integer>();
            fileReader = new FileReader(f);
            BufferedReader preStopFile = new BufferedReader(fileReader);
            String stopLinePreOrder = preStopFile.readLine();
            while (stopLinePreOrder != null) {
                list.add(new Integer(stopLinePreOrder.trim()));
                stopLinePreOrder = preStopFile.readLine();
            }
            preStopFile.close();
            return Collections.unmodifiableList(list);

        } catch (IOException ex) {
    
        	 if(debug){
           	  System.out.println(ex.toString());
             }
        	
        	return null;

        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
              
            	 if(debug){
               	  System.out.println(ex.toString());
                 }
               return null;
            }
        }



    }

    public static void writeListToFile(File f, List<Integer> list, boolean order, boolean debug) {

        if (order) {

            Collections.sort(list);
        }

        FileWriter fileWriter = null;
        PrintWriter pr = null;
        try {
            fileWriter = new FileWriter(f);
            pr = new PrintWriter(fileWriter);
            for (Integer item : list) {
                pr.write(item + "\n");
            }



        } catch (IOException ex) {
          if(debug){
        	  System.out.println(ex.toString());
          }

        } finally {
            try {
                pr.close();
                fileWriter.close();
            } catch (IOException ex) {
            	 if(debug){
               	  System.out.println(ex.toString());
                 }
               
            }
        }



    }

   
    public static void writeNodeListToFile(File f, List<? extends Node> list,boolean debug) {


        FileWriter fileWriter = null;
        PrintWriter pr = null;
        try {
            fileWriter = new FileWriter(f);
            pr = new PrintWriter(fileWriter);
            for (Object item : list) {
                pr.write(item.toString() + "\n");
            }



        } catch (IOException ex) {
          
        	if(debug){
        		
        		System.out.println(ex.toString());
        	}

        } finally {
            try {
                pr.close();
                fileWriter.close();
            } catch (IOException ex) {
     
            	if(debug){
            		
            		System.out.println(ex.toString());
            	}
            	
            }
        }



    }

    
   
    

public static void writeListToFile(File f, List<Object> list,boolean debug) {


    FileWriter fileWriter = null;
    PrintWriter pr = null;
    try {
        fileWriter = new FileWriter(f);
        pr = new PrintWriter(fileWriter);
        for (Object item : list) {
            pr.write(item.toString() + "\n");
        }



    } catch (IOException ex) {
      
    	if(debug){
    		
    		System.out.println(ex.toString());
    	}

    } finally {
        try {
            pr.close();
            fileWriter.close();
        } catch (IOException ex) {
 
        	if(debug){
        		
        		System.out.println(ex.toString());
        	}
        	
        }
    }



}





public static void writeListToFile(File f, Collection<String> list) {


    FileWriter fileWriter = null;
    PrintWriter pr = null;
    try {
        fileWriter = new FileWriter(f);
        pr = new PrintWriter(fileWriter);
        for (String item : list) {
            pr.write(item.toString() + "\n");
        }



    } catch (IOException ex) {
      
    	System.out.print(ex.toString());
    	

    } finally {
        try {
            pr.close();
            fileWriter.close();
        } catch (IOException ex) {
 
        	
        }
    }



}

public static void writeListToFile(File f, Collection<String> list,String terminator) {


    FileWriter fileWriter = null;
    PrintWriter pr = null;
    try {
        fileWriter = new FileWriter(f);
        pr = new PrintWriter(fileWriter);
        for (String item : list) {
            pr.write(item.toString() + terminator);
        }



    } catch (IOException ex) {
      
    	

    } finally {
        try {
            pr.close();
            fileWriter.close();
        } catch (IOException ex) {
 
        	
        }
    }



}





/**
 * Returns all jpg images from a directory in an array.
 *
 * @param directory                 the directory to start with
 * @param descendIntoSubDirectories should we include sub directories?
 * @return an ArrayList<String> containing all the files or nul if none are found..
 * @throws IOException
 */
public static ArrayList<File> getAllImages(File directory, boolean descendIntoSubDirectories, String  hiddenFileSuffix) throws IOException {
    ArrayList<File> resultList = new ArrayList<File>();
    File[] f = directory.listFiles();
    for (File file : f) {
        if (file != null && file.getName().toLowerCase().endsWith(".jpg") && !file.getName().startsWith("tn_")&& !file.getName().startsWith(hiddenFileSuffix)) {
            resultList.add(file);
        }
        if (descendIntoSubDirectories && file.isDirectory()) {
            ArrayList<File> tmp = getAllImages(file, true, hiddenFileSuffix);
            if (tmp != null) {
                resultList.addAll(tmp);
            }
        }
    }
    if (resultList.size() > 0)
        return resultList;
    else
        return null;
}






public static ArrayList<File> getAllFile(File directory, boolean descendIntoSubDirectories,String endofFile, String  hiddenFileSuffix) throws IOException {
   ArrayList<File> resultList = new ArrayList<File>();
   File[] f = directory.listFiles();
   for (File file : f) {
       if (file != null && file.getName().toLowerCase().endsWith(endofFile) && !file.getName().startsWith("tn_")&& !file.getName().startsWith(hiddenFileSuffix)) {
           resultList.add(file);
       }
       if (descendIntoSubDirectories && file.isDirectory()) {
           ArrayList<File> tmp = getAllFile(file, true,endofFile,hiddenFileSuffix);
           if (tmp != null) {
               resultList.addAll(tmp);
           }
       }
   }
   if (resultList.size() > 0)
       return resultList;
   else
       return null;
}


public static ArrayList<File> getAllFileWithName(File directory, boolean descendIntoSubDirectories,String name, String  hiddenFileSuffix) throws IOException {
	   ArrayList<File> resultList = new ArrayList<File>();
	   File[] f = directory.listFiles();
	   for (File file : f) {
	       if (file != null && file.getName().toLowerCase().trim().equals(name) && !file.getName().startsWith("tn_")&& !file.getName().startsWith(hiddenFileSuffix)) {
	           resultList.add(file);
	       }
	       if (descendIntoSubDirectories && file.isDirectory()) {
	           ArrayList<File> tmp = getAllFile(file, true,name,hiddenFileSuffix);
	           if (tmp != null) {
	               resultList.addAll(tmp);
	           }
	       }
	   }
	   if (resultList.size() > 0)
	       return resultList;
	   else
	       return null;
	}



public static ArrayList<File> getAllFile(File directory, boolean descendIntoSubDirectories,Pattern pattern, String  hiddenFileSuffix) throws IOException {
	   ArrayList<File> resultList = new ArrayList<File>();
	   File[] f = directory.listFiles();
	   Matcher matcher=null;
	   for (File file : f) {
	       
		    matcher=pattern.matcher(file.getName().toLowerCase().trim());
		   
		   if (file != null && matcher.find()&& !file.getName().startsWith("tn_")&& !file.getName().startsWith(hiddenFileSuffix)) {
	           resultList.add(file);
	       }
	       if (descendIntoSubDirectories && file.isDirectory()) {
	           ArrayList<File> tmp = getAllFile(file, true,pattern,hiddenFileSuffix);
	           if (tmp != null) {
	               resultList.addAll(tmp);
	           }
	       }
	   }
	   if (resultList.size() > 0)
	       return resultList;
	   else
	       return null;
	}




public static ArrayList<File> getAllDir(File directory, boolean descendIntoSubDirectories) throws IOException {
	   ArrayList<File> resultList = new ArrayList<File>();
	   File[] f = directory.listFiles();
	   for (File file : f) {
	       if (file != null&& file.isDirectory()) {
	           resultList.add(file);
	       }
	       if (descendIntoSubDirectories && file.isDirectory()) {
	           ArrayList<File> tmp = getAllDir(file, true);
	           if (tmp != null) {
	               resultList.addAll(tmp);
	           }
	       }
	   }
	   if (resultList.size() > 0)
	       return resultList;
	   else
	       return null;
	}







public static ArrayList<File> getAllDir(File directory, String notName,boolean descendIntoSubDirectories) throws IOException {
	   ArrayList<File> resultList = new ArrayList<File>();
	   File[] f = directory.listFiles();
	   for (File file : f) {
	       if (file != null&& file.isDirectory()&&!file.getName().equals(notName)) {
	           resultList.add(file);
	       }
	       if (descendIntoSubDirectories && file.isDirectory()) {
	           ArrayList<File> tmp = getAllDir(file,notName,true);
	           if (tmp != null) {
	               resultList.addAll(tmp);
	           }
	       }
	   }
	   if (resultList.size() > 0)
	       return resultList;
	   else
	       return null;
	}












public static void writeProperties(File f,Map<String,Double>map){
	
	  FileWriter fileWriter = null;
      PrintWriter pr = null;
      try {
          fileWriter = new FileWriter(f);
          pr = new PrintWriter(fileWriter);
          
          Set<String> set=map.keySet();
          for (String item : set) {
              pr.write(item +" = "+ map.get(item)+"\n");
          }



      } catch (IOException ex) {
       

      } finally {
          try {
              pr.close();
              fileWriter.close();
          } catch (IOException ex) {
          	
             
          }
      }
	
	
	
}






public static void serializeObject(Object object,File f) throws IOException{
	
        //use buffering
        OutputStream file = new FileOutputStream(f );
        OutputStream buffer = new BufferedOutputStream( file );
        ObjectOutput output = new ObjectOutputStream( buffer );
       
          output.writeObject(object);
        
        
          output.close();
        
     

	
}


public static Object deserialiseObject(File f) throws IOException, ClassNotFoundException{
	
	
	
	//use buffering
    InputStream file = new FileInputStream( f );
    InputStream buffer = new BufferedInputStream( file );
    ObjectInput input = new ObjectInputStream ( buffer );
    
      //deserialize the List
     Object object= input.readObject();
  
   
      input.close();
      
      return object;
 
}

public static void writeListToFile(File resultTagList, Set<String> allTags) {
	  FileWriter fileWriter = null;
	    PrintWriter pr = null;
	    try {
	        fileWriter = new FileWriter(resultTagList);
	        pr = new PrintWriter(fileWriter);
	        int count=allTags.size()-1;
	        for (String item : allTags) {
	           if(!item.equals(" ")){
	        	if(count!=0)
	        	pr.write(item+"\n");
	            else{
	            pr.write(item);
		            	
	            }
	           }	
	         count--;
	        }



	    } catch (IOException ex) {
	      
	    	

	    } finally {
	        try {
	            pr.close();
	            fileWriter.close();
	        } catch (IOException ex) {
	 
	        	
	        }
	    }



	
}


public static void writeListTextFileIntoDir(File dir, Collection<TextFile> files) {
	    
  	
	 int index=0;   
	for(TextFile item : files){
	
	
	        
		    File  file= new File(dir+File.separator+item.getReference().toString());
               
		    
		    try {
				FileUtils.writeStringToFile(file, item.getBody());
			
		       int a=index++ % 10;
				if (a==1)
			        System.gc();

		    
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	    	    	
	    
	   
	

	}     

	
}

public static void writeListTextFileIntoDirClassInfo(
		File dir, List<TextFile> files) {

	int index=0;   
		for(TextFile item : files){
		
		
			String name=item.getReference().toString().split("\\.")[0]+"_"+item.getClas()+".txt";
			
		        
			    File  file= new File(dir+File.separator+name);
	               
			    
			    try {
					FileUtils.writeStringToFile(file, item.getBody());
				
			    
				    int a=index++ % 10;
					if (a==1)
				        System.gc();

			    
			    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}	
}

public static void save(File f, AnnotationObject annotationObject) {
	// TODO Auto-generated method stub

	
	ObjectOutputStream writer=null;
	try {
		writer = new ObjectOutputStream(new FileOutputStream(f));
		
		
		writer.writeObject(annotationObject);
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	finally{
		
		if(writer!=null){
			
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	
	
}


public static AnnotationObject  readAnnotationObject(File f) {
	// TODO Auto-generated method stub

	
	ObjectInputStream reader=null;
	AnnotationObject objet=null;
	try {
		reader = new ObjectInputStream(new FileInputStream(f));
		
	   objet=(AnnotationObject) reader.readObject();
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	finally{
		
		if(reader!=null){
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	return objet;
	
}



	


public static <T> void save(File f, T o) {
	// TODO Auto-generated method stub

	
	ObjectOutputStream writer=null;
	try {
		writer = new ObjectOutputStream(new FileOutputStream(f));
		
		
		writer.writeObject(o);
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	finally{
		
		if(writer!=null){
			
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	
	
}


public static  Map<String, List<String>> readHashMapStringListString(File f) {
	// TODO Auto-generated method stub

	
	ObjectInputStream reader=null;
	
	Map<String,List<String>> map=null;
	try {
		reader = new ObjectInputStream(new FileInputStream(f));
		
	   map=(Map<String,List<String>> )reader.readObject();
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	finally{
		
		if(reader!=null){
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	return map;
	
}

}


