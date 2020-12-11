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

function createRequirementTable() {

}