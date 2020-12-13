var XMLDoc;
readXML();

$("#searchBtn").on("click", function() {
    validateId($("#searchInput").val());
    $("#searchInput").val("");
})

$("#searchInput").on("keypress", function(e) {
    if (e.which == 13) {
        if ($(this).val() == "") {
            resetPage();
        }
        else {
            validateId($(this).val());
        }
    }
})

function validateId(id) {
    var projects = XMLDoc.getElementsByTagName("project");
    var projectId = 0;
    for (var i = 0; i < projects.length; i++) {
        if(projects[i].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            projectId = projects[i].getElementsByTagName("id")[0].childNodes[0].nodeValue;
            break;
        }
    }
    if (projectId != 0) {
        createRequirementTable(projectId);
    }
    else if (id != "") {
        alert("Project id '" + id + "' was not found...");
    }
}

$("#clearBtn").on("click", function() {
    resetPage();
})

function resetPage() {
    $("#searchInput").val("");
    document.getElementById("project-title").innerHTML = "";
    document.getElementById("project-status").innerHTML = "";
    document.getElementById("project-deadline").innerHTML = "";
    createProjectTable();
}

function getRequirementId(i, id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var j = 0; j < projects.length; j++) {
        if (projects[j].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[j];
            break;
        }
    }
    var req = project.getElementsByTagName("requirement");
    return req[i].getElementsByTagName("id")[0].childNodes[0].nodeValue;
}

function getRequirementPriority(i, id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var j = 0; j < projects.length; j++) {
        if (projects[j].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[j];
            break;
        }
    }
    var req = project.getElementsByTagName("requirement");
    return req[i].getElementsByTagName("priority")[0].childNodes[0].nodeValue;
}

function getRequirementType(i, id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var j = 0; j < projects.length; j++) {
        if (projects[j].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[j];
            break;
        }
    }
    var req = project.getElementsByTagName("requirement");
    return req[i].getElementsByTagName("type")[0].childNodes[0].nodeValue;
}

function getRequirementDeadline(i, id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var j = 0; j < projects.length; j++) {
        if (projects[j].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[j];
            break;
        }
    }
    var req = project.getElementsByTagName("requirement");
    return req[i].getElementsByTagName("deadline")[0].childNodes[0].nodeValue;
}

function getRequirementStatus(i, id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var j = 0; j < projects.length; j++) {
        if (projects[j].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[j];
            break;
        }
    }
    var req = project.getElementsByTagName("requirement");
    return req[i].getElementsByTagName("status")[0].childNodes[0].nodeValue;
}

function createProjectTable() {
    var projects = XMLDoc.getElementsByTagName("project");
    var table = "<tr><th>Project Id</th><th>Title</th><th>Deadline</th><th>Status</th></tr>";
    for (var i = 0; i < projects.length; i++) {
        table += 
        "<tr><td>" + projects[i].getElementsByTagName("id")[0].childNodes[0].nodeValue + 
        "</td><td>" + projects[i].getElementsByTagName("title")[0].childNodes[0].nodeValue + 
        "</td><td>" + projects[i].getElementsByTagName("deadline")[0].childNodes[0].nodeValue + 
        "</td><td>" + projects[i].getElementsByTagName("status")[0].childNodes[0].nodeValue + "</td></tr>";
    }
    document.getElementById("project-id").innerHTML = "Projects"
    document.getElementById("table").innerHTML = table;
}

function createRequirementTable(id) {
    var projects = XMLDoc.getElementsByTagName("project");
    for (var i = 0; i < projects.length; i++) {
        if (projects[i].getElementsByTagName("id")[0].childNodes[0].nodeValue == id) {
            var project = projects[i];
        }
    }
    var reqs = project.getElementsByTagName("requirement");
    var table = "<th>Requirement Id</th><th>Priority</th><th>Type</th><th>Deadline</th><th>Status</th>";
    for (var j = 0; j < reqs.length; j++) {
        table += "<tr><td>" + getRequirementId(j, id) + "</td>";
        table += "<td>" + getRequirementPriority(j, id) + "</td>";
        table += "<td>" + getRequirementType(j, id) + "</td>";
        table += "<td>" + getRequirementDeadline(j, id) + "</td>";
        table += "<td>" + getRequirementStatus(j, id) + "</td></tr>";
    }
    document.getElementById("table").innerHTML = table;
    document.getElementById("project-id").innerHTML = id;
    document.getElementById("project-title").innerHTML = "<b>Title:</b> " + project.getElementsByTagName("title")[0].childNodes[0].nodeValue;
    document.getElementById("project-status").innerHTML = "<b>Status:</b> " + project.getElementsByTagName("status")[0].childNodes[0].nodeValue;
    document.getElementById("project-deadline").innerHTML = "<b>Deadline:</b> " + project.getElementsByTagName("deadline")[0].childNodes[0].nodeValue;
}

function readXML() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            XMLDoc = this.responseXML;
            createProjectTable();
        }
    };
    xhttp.open("GET", "../../src/files/model.xml", true);
    xhttp.send();
}
