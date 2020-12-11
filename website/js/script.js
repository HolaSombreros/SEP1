var text = "<projectlist><project><id>1</id><title>Hooga</title><status>Not Started</status></project>" + 
                        "<project><id>3</id><title>Booga</title><status>Started</status></project></projectlist>";

console.log(getProjectTitle(0));
console.log(getProjectTitle(1));

function getProjectId(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    return val[i].childNodes[0].childNodes[0].nodeValue;
}

function getProjectTitle(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    return val[i].childNodes[1].childNodes[0].nodeValue;
}

function getProjectStatus(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    return val[i].childNodes[2].childNodes[0].nodeValue;
}

function getRequirementId(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("requirement");
    return val[i].childNodes[0].childNodes[0].nodeValue;
}

function getRequirementType(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("requirement");
    return val[i].childNodes[7].childNodes[0].nodeValue;
}

function getRequirementDeadline(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("requirement");
    return val[i].childNodes[5].childNodes[0].nodeValue;
}

function getRequirementStatus(i) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("requirement");
    return val[i].childNodes[6].childNodes[0].nodeValue;
}

function createProjectTable() {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    for (i = 0; i < val.length; i++) {
        table += "<tr><td class="
    }
}

function readXML()
{
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function ()
  {
    if (this.readyState == 4 && this.status == 200)
    {
      showData(this);
    }
  };
  xhttp.open("GET", "../../src/files/model.xml", true);
  xhttp.send();
}

function createRequirementTable(xml) {
    var i;
    var xmlDoc = xml.responseXML;
    var x = xmlDoc.getElementsByTagName("project");
    for (i=0;i<x.lenght;i++)
      if (x[i].getElementsByTagName("id") == some function to input id)
        var project = x[i];
    var listLenght = project.length;
    var tableReq = "<table><th>Requirement Id</th><th>Type</th><th>Deadline</th><th>Status</th>";
    for (i = 0 ; i <x.length; i++) {
        tableReq += "<tr><td>" + getRequirementId(i) + "</td>";
        tableReq += "<td>" + getRequirementType(i) + "</td>";
        tableReq += "<td>" + getRequirementDeadline(i) + "</td>";
        tableReq += "<td>" + getRequirementStatus(i) "</td></tr>";
    }
    table2 += "</table>"
    document.getElementById("tableReq").innerHTML = table2:
}

readXML();