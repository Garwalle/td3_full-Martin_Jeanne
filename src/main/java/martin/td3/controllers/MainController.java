package martin.td3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import io.github.jeemv.springboot.vuejs.utilities.JsArray;
import martin.td3.repositories.OrgaRepository;

@Controller
public class MainController {

	@Autowired
	private VueJS vue;
	
	@Autowired
	private OrgaRepository orgaRepo;
	
	@ModelAttribute(name = "vue")
    private VueJS getVue() {
        return this.vue;
    }

	private final String restOrgaURL = "'http://127.0.0.1:8080/orga/'";

	@RequestMapping("vue")
	public String index(ModelMap model) {
		vue.addData("orgaRepo", orgaRepo.findAll());
		vue.addDataRaw("orga", "{}");
		vue.addData("addDialog", false);
		
		vue.addMethod("postOrga", "let self=this;" + Http.post(restOrgaURL, "self.orga", "self.orgaRepo.push(response.data);self.addDialog=false;", "console.log(response)"));
		vue.addMethod("deleteOrga", "let self=this;" + Http.delete(restOrgaURL + "+orga.id", JsArray.remove("self.orgas", "orga")),"orga");
		
		/*vue.addDataRaw("headers", " [{\r\n" + 
				"                        \"text\": \"Organizations\",\r\n" + 
				"                        \"align\": \"start\",\r\n" + 
				"                        \"sortable\": false,\r\n" + 
				"                        \"value\": \"name\"\r\n" + 
				"                    }, {\r\n" + 
				"                        \"text\": \"Aliases\",\r\n" + 
				"                        \"value\": \"aliases\"\r\n" + 
				"                    }, {\r\n" + 
				"                        \"text\": \"Domain\",\r\n" + 
				"                        \"value\": \"domain\"\r\n" + 
				"                    }, {\r\n" + 
				"                        \"text\": \"Actions\",\r\n" + 
				"                        \"value\": \"actions\"\r\n" + 
				"                    }, {\r\n" + 
				"                        \"value\": \"data-table-expand\"\r\n" + 
				"                    }]");*/
		
		
		// For testing, to delete
				vue.addData("dialog", false);
				vue.addData("dialogDelete", false);
				
				vue.addDataRaw("headers", " [{\r\n" + 
						"          text: \'Dessert (100g serving)\',\n" + 
						"          align: \'start\',\n" + 
						"          sortable: false,\n" + 
						"          value: \'name\',\n" + 
						"        },\n" + 
						"        { text: \'Calories\', value: \'calories\' },\n" + 
						"        { text: \'Fat (g)\', value: \'fat\' },\n" + 
						"        { text: \'Carbs (g)\', value: \'carbs\' },\n" + 
						"        { text: \'Protein (g)\', value: \'protein\' },\n" + 
						"        { text: \'Actions\', value: \'actions\', sortable: false" +
						"		 }]");
				
				vue.addDataRaw("desserts", "[]");
				vue.addData("editedIndex", -1);
				vue.addData("editedItem", "{\n" + 
						"        name: '',\n" + 
						"        calories: 0,\n" + 
						"        fat: 0,\n" + 
						"        carbs: 0,\n" + 
						"        protein: 0,\n" + 
						"      },\n" + 
						"      defaultItem: {\n" + 
						"        name: '',\n" + 
						"        calories: 0,\n" + 
						"        fat: 0,\n" + 
						"        carbs: 0,\n" + 
						"        protein: 0,\n" + 
						"      },\n" + 
						"    }");
				
				vue.addComputed("formTitle", "return this.editedIndex === -1 ? \'New Item\' : \'Edit Item\'");
				vue.addWatcher("dialog", "val || this.close()", "val");
				vue.addWatcher("dialogDelete", "val || this.closeDelete()", "val");
				vue.onCreated("this.initialize()");
				vue.addMethod("initialize ", "this.desserts = [\n" + 
						"          {\n" + 
						"            name: \'Frozen Yogurt\',\n" + 
						"            calories: 159,\n" + 
						"            fat: 6.0,\n" + 
						"            carbs: 24,\n" + 
						"            protein: 4.0,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Ice cream sandwich\',\n" + 
						"            calories: 237,\n" + 
						"            fat: 9.0,\n" + 
						"            carbs: 37,\n" + 
						"            protein: 4.3,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Eclair\',\n" + 
						"            calories: 262,\n" + 
						"            fat: 16.0,\n" + 
						"            carbs: 23,\n" + 
						"            protein: 6.0,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Cupcake\',\n" + 
						"            calories: 305,\n" + 
						"            fat: 3.7,\n" + 
						"            carbs: 67,\n" + 
						"            protein: 4.3,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Gingerbread\',\n" + 
						"            calories: 356,\n" + 
						"            fat: 16.0,\n" + 
						"            carbs: 49,\n" + 
						"            protein: 3.9,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Jelly bean\',\n" + 
						"            calories: 375,\n" + 
						"            fat: 0.0,\n" + 
						"            carbs: 94,\n" + 
						"            protein: 0.0,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Lollipop\',\n" + 
						"            calories: 392,\n" + 
						"            fat: 0.2,\n" + 
						"            carbs: 98,\n" + 
						"            protein: 0,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Honeycomb\',\n" + 
						"            calories: 408,\n" + 
						"            fat: 3.2,\n" + 
						"            carbs: 87,\n" + 
						"            protein: 6.5,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'Donut\',\n" + 
						"            calories: 452,\n" + 
						"            fat: 25.0,\n" + 
						"            carbs: 51,\n" + 
						"            protein: 4.9,\n" + 
						"          },\n" + 
						"          {\n" + 
						"            name: \'KitKat\',\n" + 
						"            calories: 518,\n" + 
						"            fat: 26.0,\n" + 
						"            carbs: 65,\n" + 
						"            protein: 7,\n" + 
						"          }]");
				
				vue.addMethod("editItem", "this.editedIndex = this.desserts.indexOf(item)\n" + 
						"        this.editedItem = Object.assign({}, item)\n" + 
						"        this.dialog = true");
				
				vue.addMethod("deleteItem", "this.editedIndex = this.desserts.indexOf(item)\n" + 
						"        this.editedItem = Object.assign({}, item)\n" + 
						"        this.dialogDelete = true","item");
				
				vue.addMethod("deleteItemConfirm", "this.desserts.splice(this.editedIndex, 1)\n" + 
						"        this.closeDelete()");
				
				vue.addMethod("close ", "this.dialog = false\n" + 
						"        this.$nextTick(() => {\n" + 
						"          this.editedItem = Object.assign({}, this.defaultItem)\n" + 
						"          this.editedIndex = -1");
				
				vue.addMethod("closeDelete ", "this.dialogDelete = false\n" + 
						"        this.$nextTick(() => {\n" + 
						"          this.editedItem = Object.assign({}, this.defaultItem)\n" + 
						"          this.editedIndex = -1");
				
				vue.addMethod("save", "if (this.editedIndex > -1) {\n" + 
						"          Object.assign(this.desserts[this.editedIndex], this.editedItem)\n" + 
						"        } else {\n" + 
						"          this.desserts.push(this.editedItem)\n" + 
						"        }\n" + 
						"        this.close()");
				
				
		
		// TODO : confirmation avant supression, quand on click sur une organisation on voit les groupes dans l'organisation
		model.put("vue", vue);
		return "index";
	}
}