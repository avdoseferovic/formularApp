let fieldSpan = document.getElementById("field");

let tempLabel = 'Label 1';
let currentFieldId = 0;
let currentFormField = 0;
let formVersion = null;

let formName = '';


let textselected = "";
let checkselected = "";
let radioselected = "";

let formArray = [];
let objArray = [];

let timer = 0;
let secondTimer = 1;


function fieldObj(){
	this.fieldRadios = []
	this.formName = formName;
}

fieldObj.prototype = {
	"formName": formName,
	"fieldId": 1,
	"fieldName": "Label 1",
	"fieldType": 'text',
	"fieldValue": "",
	"fieldMandatory": false,
	"fieldRadios": []
};

let defaultObj = new fieldObj(1, "Label1");



let ref = document.getElementById(`spanAfter${timer}`);


//Handles input change for label
function handleName (obj){
	if(obj.name === "staticId"){
		defaultObj.fieldName = obj.value;
	}
	else{
		currentFieldId = obj.name;
		objArray[currentFieldId - 1].fieldName = obj.value;
	}
	console.log(objArray);

}

function handleMandSelect(obj){
	if(obj.name === "staticMandId"){
		defaultObj.fieldMandatory = obj.value;
	}
	else{
		objArray[currentFieldId].fieldName = obj.value;
	}
	console.log(objArray);
}

//Handles the first dropdown menu
function handleSelect (obj){
	currentFieldId = obj.name;
	console.log(`first select: ${obj.name}`)
	if(obj.value === "radio"){
		let radioRef;
		if(obj.name === "staticTypeId"){
			radioRef = document.getElementById("firstSelect");
		}
		else{
			radioRef = document.getElementById(obj.id);
			document.getElementById(`div${Number(obj.name) + 1}`).setAttribute("class", "radioDown");
		}
		let newRadio = document.createElement('select');

		newRadio.setAttribute("class", "radio");
		newRadio.setAttribute("id", currentFieldId);
		newRadio.setAttribute("name", currentFieldId);
		newRadio.setAttribute("onchange", "handleRadio(this)");

		newRadio.innerHTML = `	<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>`;

		insertAfter(newRadio, radioRef);

		let newInputSpan = document.createElement('span');
		newInputSpan.setAttribute("class", "radioDrop");
		if(obj.name === "staticTypeId"){
			newInputSpan.setAttribute("id", "radioDropId0");
		}
		else{
			newInputSpan.setAttribute("id", `radioDropId${Number(obj.name) + 1}`);
		}

		insertAfter(newInputSpan, radioRef);

		createRadioInput(obj, 0)
		
	}
	else{
		let newInputSpan = document.getElementById(`radioContainer${Number(obj.name) +1}`);

		if(obj.name === "staticTypeId"){
			newInputSpan = document.getElementById("radioContainer");

		}
		else{
			document.getElementById(`div${Number(obj.name) +1}`).setAttribute("class", "none");
			console.log(`obj.name---${obj.name}`);
		}

		while(newInputSpan.lastElementChild.id !== "firstSelect" &&
			  newInputSpan.lastElementChild.id !== `select${obj.name}`){
				  console.log(newInputSpan.lastElementChild.id)
			newInputSpan.removeChild(newInputSpan.lastChild);
		}
		console.log(newInputSpan);
		
	}
	if(currentFieldId === "staticTypeId"){
		defaultObj.fieldType = obj.value;
	}
	else{
		objArray[currentFieldId].fieldType = obj.value;
	}
}

//Handles/creates the number of radio inputs
function handleRadio(obj){
	currentFieldId = obj.name;
	console.log(obj.value);
	console.log(`obj.name ${obj.name}`)
	let newInputSpan;

	if(currentFieldId === "staticTypeId"){
		newInputSpan = document.getElementById("radioDropId0");
	}
	else{
		newInputSpan = document.getElementById("radioDropId" + objArray[currentFieldId].fieldId);
	}

	let radioTimer = obj.value;
	newInputSpan.innerHTML = '';
	for(let i = 0; i < radioTimer; i++){
		createRadioInput(obj, i);
	}
}
//Handles radio input
function handleRadioInput(obj){
	console.log(`currentFieldId: ${currentFieldId}`);
	if(obj.name === "staticRadioInputstaticTypeId"){
		defaultObj.fieldRadios[obj.id - 1] = obj.value;
	}
	else{
		currentFieldId = obj.name.replace('staticRadioInput', '');

		objArray[currentFieldId].fieldRadios[obj.id - 1] = obj.value;
	}
	console.log(objArray);
}

function createRadioInput(obj, i){
	let newInputSpan;
	console.log(obj);
	console.log(currentFieldId);
	console.log(obj.name);
	if(currentFieldId === "staticTypeId"){
		newInputSpan = document.getElementById("radioDropId0");
		defaultObj.fieldRadios[i] = "";
	}
	else{
		newInputSpan = document.getElementById("radioDropId" + objArray[currentFieldId].fieldId);
		if(objArray[currentFieldId].fieldRadios.length < 1){
			objArray[currentFieldId].fieldRadios[i] = "";
		}
	}

	let newInput = document.createElement('input');
		newInput.setAttribute("id", i + 1);
		newInput.setAttribute("name", `staticRadioInput${currentFieldId}`);
		newInput.setAttribute("type", "text");
		newInput.setAttribute("class", "text");
		if(currentFieldId !== "staticTypeId"){
			newInput.setAttribute("value", objArray[currentFieldId].fieldRadios[i]);
		}
		newInput.setAttribute("onchange", "handleRadioInput(this)");
		newInputSpan.appendChild(newInput);

	
}




function insertAfter(el, referenceNode) {
	referenceNode.parentNode.insertBefore(el, referenceNode.nextSibling);
}


function handleClick(){
	
	tempLabel = `Label ${timer+1}`;

	//Static element always uses defaultObj
	defaultObj.fieldId = secondTimer;
	defaultObj.formName = formName;

	objArray[timer] = new fieldObj();
	objArray[timer] = JSON.parse(JSON.stringify(defaultObj)); //copy defaultObj to objArray[timer]
	
	if(objArray[timer].fieldType === undefined){
		objArray[timer].fieldType = "text";
	}

	if(objArray[timer].fieldName === undefined){
		objArray[timer].fieldName = tempLabel;
	}
	
	console.log(objArray);

	loadElements();


	timer++;
	secondTimer++;

	defaultObj.fieldRadios = [];

	checkselected = "";
	radioselected = "";
	textselected = "";

}
function handleLoad(){
	
	const Url = 'http://localhost:8080/formularapp/SearchFormular';

	const fetchParams = {
		headers:{
			"content-type":"application/json; charset=UTF-8"
		},
		method: 'post',
		body: JSON.stringify(formName)
	  }

	fetch(Url, fetchParams)
	.then(data=>{return data.json()})
	.then(res=>{
		if(res === "created"){
			console.log(res);
		}
		else{
			console.log(res);
			objArray = res;
			loadE();
		}
	})
	.catch(error=>console.log(error));

	
}


function loadE(){
	for(let i=0; i< objArray.length; i++){
		loadElements();

		timer++;
		secondTimer++;
		
		checkselected = "";
		radioselected = "";
		textselected = "";
	}
	timer = objArray.length;
	secondTimer = timer + 1;
}


function handleSearch(obj){
	formName = obj.value;
}

function handleAdmin(){
	//Reload page -- needs rework
	window.location.reload();
}

function loadFormElements(){
	let formElement = document.getElementById("adminForm");

	for(let i = 0; i < formArray.length; i++){
		let inputElement = document.createElement('input');
		inputElement.setAttribute("class",formArray[i].fieldType);
		if(formArray[i].fieldType === "radio"){
			inputElement = document.createElement('span');
			for(let j = 0; j < formArray[i].fieldRadios.length; j++){

				let inputRadioElement = document.createElement('input');
				inputRadioElement.setAttribute("type", "radio");
				inputRadioElement.setAttribute("name", i);
				inputRadioElement.setAttribute("value", j);

				let radioLabel = document.createElement('span');
				radioLabel.setAttribute("style", "display: block");

				let radioText = document.createElement('span')
				radioText.innerText = formArray[i].fieldRadios[j];
				radioLabel.appendChild(inputRadioElement);
				insertAfter(radioText, inputRadioElement);
				inputElement.appendChild(radioLabel);
				inputElement.setAttribute("class", "radioAdmin");

			}
		}

		inputElement.setAttribute("type", formArray[i].fieldType);
		inputElement.addEventListener('change', (event) => {

			if(formArray[i].fieldType === "checkbox"){
				if (event.target.checked) {
					formArray[i].fieldValue = "checked";
				} else {
					formArray[i].fieldValue = "not checked";
				}
			}
			else{
				formArray[i].fieldValue = event.target.value;
			}
		});


		let spanElement = document.createElement('span');
		spanElement.setAttribute("class", "formField");
		spanElement.setAttribute("id", i);
		spanElement.setAttribute("onClick", "handleSpanClick(this)")
		spanElement.innerText = formArray[i].fieldName;

		spanElement.appendChild(inputElement);


		formElement.appendChild(spanElement);
	}
}
function handleFormInputChange(obj){
	
	console.log(obj.value);
}

function handleSpanClick(obj){
	currentFormField = obj.id;
	console.log("fieldID: " + currentFormField)
}

function loadElements (){

	let staticField = document.getElementById("staticField");
	staticField.value = `Label ${timer +1}`;

	let staticElement = document.getElementById("staticElement");
	staticElement.innerHTML = `Element ${secondTimer + 1}:`;

	if(objArray[timer].fieldType === "text"){
		textselected = "selected";
	}
	else if(objArray[timer].fieldType === "checkbox"){
		checkselected = "selected";
	}
	else{
		radioselected = "selected";
	}

	let newEl = document.createElement('span');
	newEl.setAttribute("class", "eleClass" );
	console.log(objArray[timer].fieldName);
	newEl.setAttribute("id", "spanAfter" + secondTimer);
	newEl.innerHTML += `<div style="width: 100%" id="div${secondTimer}">Element ${secondTimer}: <input type="text" name="${secondTimer}" value="${objArray[timer].fieldName}" class="text" onchange="handleName(this)">
					<span class="radioContainer" id="radioContainer${secondTimer}">
					<select name="${timer}" id="select${timer}" onChange="handleSelect(this)">
						<option value="text" ${textselected}>Textbox</option>
						<option value="checkbox" ${checkselected}>Checkbox</option>
						<option value="radio" ${radioselected}>Radio buttons</option>
					</select>
					</span>
					<br><br></div>`;
	insertAfter(newEl, ref);


	let mandEle = document.getElementById(`radioContainer${secondTimer}`);

	let newMandSelect = document.createElement('select');

	newMandSelect.innerHTML = `	<option value="none">None</option>
								<option value="mandatory">Mandatory</option>
								<option value="numeric">Numeric</option>`
	insertAfter(newMandSelect, mandEle);

	if(objArray[timer].fieldType === "radio"){
		let tempElement = document.getElementById(`select${timer}`);

		let newInputSpan = document.createElement('span');
		newInputSpan.setAttribute("class", "radioDrop");
		if(tempElement.name === "staticTypeId"){
			newInputSpan.setAttribute("id", "radioDropId0");
		}
		else{
			newInputSpan.setAttribute("id", `radioDropId${Number(tempElement.name) + 1}`);
		}
	
		insertAfter(newInputSpan, tempElement);
		currentFieldId = timer;
		let radioTimer = objArray[timer].fieldRadios.length;
		//newInputSpan.innerHTML = '';
		document.getElementById(`div${Number(tempElement.name) +1}`).setAttribute("class", "radioDown");
		for(let i = 0; i < radioTimer; i++){
			createRadioInput(tempElement, i);
	
		}
		currentFieldId = "staticTypeId";
	}

	let radioInputRef = document.getElementById(`select${timer}`);
	if(radioselected === "selected"){
		let numEl = document.createElement('select');
		numEl.setAttribute("class", "radio");
		numEl.setAttribute("id", secondTimer);
		numEl.setAttribute("onchange", "handleRadio(this)");
		numEl.innerHTML = `	<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>`;
		insertAfter(numEl, radioInputRef);


	}


	ref = document.getElementById(`spanAfter${secondTimer}`);

}

function handleSave(){
	const Url = 'http://localhost:8080/formularapp/PostData';

	const fetchParams = {
		headers:{
			"content-type":"application/json; charset=UTF-8"
		},
		method: 'post',
		body: JSON.stringify(objArray)
	  }

	fetch(Url, fetchParams)
	.then(data=>{return data.json()})
	.then(res=>{console.log(res)})
	.catch(error=>console.log(error));

}

function handleFormSave(){
	const Url = 'http://localhost:8080/formularapp/PostFormData';

	const fetchParams = {
		headers:{
			"content-type":"application/json; charset=UTF-8"
		},
		method: 'post',
		body: JSON.stringify(formArray)
	  }

	fetch(Url, fetchParams)
	.then(data=>{return data.json()})
	.then(res=>{console.log(res)})
	.catch(error=>console.log(error));
}

function handleLoadForm (){

	if(formVersion != null){
		document.querySelectorAll(".formField").forEach(e => e.parentNode.removeChild(e));

		const Url = 'http://localhost:8080/formularapp/SearchFormular';
	
		const fetchParams = {
			headers:{
				"content-type":"application/json; charset=UTF-8"
			},
			method: 'post',
			body: JSON.stringify(formName)
		  }
	
		fetch(Url, fetchParams)
		.then(data=>{return data.json()})
		.then(res=>{
			if(res === "created"){
				console.log(res);
			}
			else{
				console.log(res);
				formArray = res;
				loadFormElements();
			}
		})
		.catch(error=>console.log(error));
	}
	else{
		alert('Insert form version!');
	}

}

function handleLoadFormVersion (){

	if(formVersion != null){
		document.querySelectorAll(".formField").forEach(e => e.parentNode.removeChild(e));

		const Url = 'http://localhost:8080/formularapp/LoadFormData';
	
		const fetchParams = {
			headers:{
				"content-type":"application/json; charset=UTF-8"
			},
			method: 'post',
			body: JSON.stringify(formVersion)
		  }
	
		fetch(Url, fetchParams)
		.then(data=>{return data.json()})
		.then(res=>{
			if(res === "created"){
				console.log(res);
			}
			else{
				console.log(res);
				//formArray = res;
				//loadFormElements();
			}
		})
		.catch(error=>console.log(error));
	}
	else{
		alert('Insert form version!');
	}

}

function loadFormular(){

	document.querySelector(".saveButton").setAttribute("onClick", "handleFormSave()");

	let forms = [];
	const Url = 'http://localhost:8080/formularapp/GetForms';

	const getParams = {
		method: 'get',
	}
	
	fetch(Url, getParams)
	.then(data=>{return data.json()})
	.then(res=>{
		console.log(res)
		forms = res;
		formName = forms[0];

		let formEle = document.getElementById("adminForm")

		while(formEle.lastChild){
				formEle.removeChild(formEle.lastChild);
			}
		let chooseEle = document.createElement('span');
		chooseEle.setAttribute("class", "loadInput")
		chooseEle.innerText = "Formular: ";

		let loadElement = document.createElement('select');
		loadElement.setAttribute("class", "select");
		loadElement.setAttribute("onChange", "handleLoadChange(this)");
		for (let i = 0; i < forms.length; i++){
			let optionElement = document.createElement('option');
			optionElement.setAttribute("value", forms[i]);
			optionElement.innerText = forms[i];

			loadElement.appendChild(optionElement);
		}

		let clickElement = document.createElement('a');
		clickElement.setAttribute("class", "button loadButton");
		clickElement.setAttribute("href", "#");
		clickElement.setAttribute("onClick", "handleLoadFormVersion()");
		clickElement.innerText = "Load";

		let inputFormElement = document.createElement('input');
		inputFormElement.setAttribute("class", "text");
		inputFormElement.setAttribute("onChange", "handleVersion(this)");

		let textVersion = document.createElement('span');
		textVersion.setAttribute("style", "padding-left: 10px;");
		textVersion.innerText = "Version: ";

		
		chooseEle.appendChild(loadElement);
		chooseEle.appendChild(textVersion);
		chooseEle.appendChild(inputFormElement);
		chooseEle.appendChild(clickElement);
		
		formEle.appendChild(chooseEle);
		
		loadFormElements();

	})
	.catch(error=>console.log(error));


}

function handleVersion(obj){
	formVersion = obj.value;

}

function handleLoadChange(obj){
	console.log(obj.value);
	formName = obj.value;
}