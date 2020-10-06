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

	private final String restOrgaURL = "http://127.0.0.1:8080/orga/";

	@RequestMapping("vue")
	public String index(ModelMap model) {
		
		vue.addMethod("postOrga", "let self=this;" + Http.post(restOrgaURL, "self.orga", "self.orgaRepo.push(response.data);self.addDialog=false;", "console.log(response)"));
		vue.addMethod("deleteOrga", "let self=this;" + Http.delete(restOrgaURL + "+orga.id", JsArray.remove("self.orgas", "orga")),"orga");
		
		//Adding Organizations list to vue's data
		vue.addData("organizations", orgaRepo.findAll());
	    //Adding Required data to vue
	    AddDatas();
	   //Adding Required computed to vue
		vue.addComputed("formTitle", "return this.editedIndex === -1 ? 'Nouvelle Organisation' : 'Editer Organisation'");
		//Adding Required methods to vue
		AddMethods();
	    model.put("vue", vue);
        return "index";
       }
	
	public void AddDatas() {
		vue.addDataRaw("headers", " [{\r\n" + 
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
				"                    }]");
		
		vue.addDataRaw("editedItem", "{ name: '',aliases: '', domain: '',settings: '',users: []}");
		vue.addData("dialog",false);
		vue.addDataRaw("defaultItem", "{ name: '',aliases: '', domain: '',settings: '',users: []}");
		vue.addData("editedIndex",-1);
	}
	
	public void AddMethods() {
		
		vue.addMethod("editItem" , "this.editedIndex = this.organizations.indexOf(item)\r\n" + 
				"      this.editedItem = Object.assign({}, item)\r\n" + 
				"      this.dialog = true","item");

		vue.addMethod("save" , "if (this.editedIndex > -1) {\r\n"
				+ "        Object.assign(this.organizations[this.editedIndex], this.editedItem)\r\n"
				+ "        this.$http['post']('http://127.0.0.1:8080/orga/'+ this.organizations[this.editedIndex].id,this.editedItem)"
				+ "      } else {\r\n"
				+ "        this.organizations.push(this.editedItem)\r\n"
				+ "        this.$http['post']('http://127.0.0.1:8080/orga/', this.editedItem)"
				+ "      }\r\n"
				+ "      this.close()");
		vue.addMethod("close" , " this.dialog = false\r\n"
				+ "      this.$nextTick(() => {\r\n"
				+ "        this.editedItem = Object.assign({}, this.defaultItem)\r\n"
				+ "        this.editedIndex = -1\r\n"
				+ "      })");
		vue.addMethod("deleteItem" , " const index = this.organizations.indexOf(item)\r\n"
				+ "      confirm('Are you sure you want to delete this item?') && this.organizations.splice(index, 1) && this.$http['delete']('http://http://127.0.0.1:8080/orgas/'+ item.id)","item");
		
		
	}
}