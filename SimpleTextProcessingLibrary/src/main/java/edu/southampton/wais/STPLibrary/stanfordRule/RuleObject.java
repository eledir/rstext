package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class RuleObject implements Iterable<String> {

	private SentenceModel sm;

	private List<String> object;

	private enum TagXObject {

		DOBJ {
			public String toString() {
				return "dobj";
			}
		},

		IOBJ {
			public String toString() {
				return "iobj";
			}
		},

	};

	private enum TagVerbObject {

		PREP_IN {
			public String toString() {
				return "prep_in";
			}
		},

		PREP_AS {
			public String toString() {
				return "prep_as";
			}
		},

	};

	public RuleObject(SentenceModel sm) {

		object = Lists.newArrayList();
		this.sm = sm;
	}

	public void run() {

		Table<String, String, String> tableDepe = this.sm.getTableDepGovern();
		
		for (TagXObject dep : TagXObject.values()) {

			Collection<String> s = tableDepe.row(dep.toString()).values();

			for (String item : s) {

				String[] itemSplit = item.split("-");

				try {

					if (POSTagStanford.isNoun(itemSplit[1])
							&& !this.object.contains(item)) {

						this.object.add(item);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		
		Collection<String> s = tableDepe.rowKeySet();	
		
		 
		for (TagVerbObject dep : TagVerbObject.values()) {

			
			if(s.contains(dep.toString())){
				
				
				System.out.println(tableDepe.columnMap().get(dep.toString()));
				
				System.out.println(tableDepe.columnMap());
				
				System.out.println(tableDepe.column(dep.toString()));
				
				
				Map<String,String> map=tableDepe.columnMap().get(dep.toString());
				
				
				for(String key: map.keySet()){
					
					
					String value=map.get(key);
					
					String[] itemSplit = key.split("-");

					try {

						if (POSTagStanford.isVerb(itemSplit[1])
								&& !this.object.contains(value)) {

							this.object.add(value);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
					
					
				}
				
				
				
			}
			
			
		}

		
		
		
		
		
		
		
		
		
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return this.object.iterator();
	}

	public List<String> getObject() {
		return object;
	}

}
