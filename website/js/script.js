var text = "<projectlist><project><id>1</id><title>Hooga</title><status>Not Started</status>" +
                        "<requirementList><requirement><id>7</id><type>Functional</type><deadline>19/02/2014</deadline><status>Ended</status></requirement>" +
                        "<requirement><id>12</id><type>Project Related</type><deadline>19/05/2018</deadline><status>Not Started</status></requirement>" +
                        "<requirement><id>9</id><type>Non Functional</type><deadline>15/02/2019</deadline><status>Started</status></requirement></requirementList></project>" +
                        "<project><id>3</id><title>Booga</title><status>Started</status>" +
                        "<requirementList><requirement><id>10</id><type>Functional</type><deadline>12/02/2014</deadline><status>Ended</status></requirement>" +
                        "<requirement><id>9</id><type>Non Functional</type><deadline>7/02/2019</deadline><status>Started</status></requirement></requirementList></project></projectlist>";
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

function getRequirementId(i,id) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    for (var j=0;j<val.length;j++)
        if(val[j].childNodes[0].childNodes[0].nodeValue == id)
            var project = val[j];
    var req = project.getElementsByTagName("requirement");
    return req[i].childNodes[0].childNodes[0].nodeValue;
}

function getRequirementType(i,id) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project")
    for (var j=0;j<val.length;j++)
        if(val[j].childNodes[0].childNodes[0].nodeValue == id)
            var project = val[j];
    var req = project.getElementsByTagName("requirement");
    return req[i].childNodes[1].childNodes[0].nodeValue;
}

function getRequirementDeadline(i,id) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project")
    for (var j=0;j<val.length;j++)
        if(val[j].childNodes[0].childNodes[0].nodeValue == id)
            var project = val[j];
    var req = project.getElementsByTagName("requirement");
    return req[i].childNodes[2].childNodes[0].nodeValue;
}

function getRequirementStatus(i,id) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project")
    for (var j=0;j<val.length;j++)
        if(val[j].childNodes[0].childNodes[0].nodeValue == id)
            var project = val[j];
    var req = project.getElementsByTagName("requirement");
    return req[i].childNodes[3].childNodes[0].nodeValue;
}

createProjectTable();
function createProjectTable() {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    let val = XMLDoc.getElementsByTagName("project");
    let tablePrj = "<table><tr><th>Project Id</th><th>Title</th><th>Status</th></tr>";
    for (var i = 0; i < val.length; i++) {
        tablePrj += "<tr><td>" + getProjectId(i) + "</td><td>" + getProjectTitle(i) + "</td><td>" + getProjectStatus(i) + "</td></tr>";
    }
    tablePrj +="</table>"
    document.getElementById("table").innerHTML = tablePrj;
}

createRequirementTable(id)
function createRequirementTable(id) {
    let parser = new DOMParser();
    let XMLDoc = parser.parseFromString(text, "text/xml");
    var x = XMLDoc.getElementsByTagName("project");
    for (var j=0;j<x.length;j++)
            if(x[j].childNodes[0].childNodes[0].nodeValue == id)
                var project = x[j];
    var y = project.getElementsByTagName("requirement");
    var tableReq = "<div><p></p><p></p><p></p></div><table><th>Requirement Id</th><th>Type</th><th>Deadline</th><th>Status</th>";
    for (var i = 0 ; i <y.length; i++) {
        tableReq += "<tr><td>" + getRequirementId(i,id) + "</td>";
        tableReq += "<td>" + getRequirementType(i,id) + "</td>";
        tableReq += "<td>" + getRequirementDeadline(i,id) + "</td>";
        tableReq += "<td>" + getRequirementStatus(i,id) + "</td></tr>";
    }
    tableReq += "</table>";
    document.getElementById("tableReq").innerHTML = tableReq;
}

/*function readXML()
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
}*/

//readXML();