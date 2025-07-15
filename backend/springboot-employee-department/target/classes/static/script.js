let employeeModal = new bootstrap.Modal(document.getElementById('employeeModal'));
let currentEmployeeId = null;

function searchEmployees() {
    const deptId = document.getElementById("deptInput").value.trim();
    if (!deptId) return alert("Please enter a department ID");

    fetch(`http://localhost:8080/api/employees/by-department/${deptId}`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById("employeeTableBody");
            tbody.innerHTML = "";
            if (!data || data.length === 0) {
                tbody.innerHTML = "<tr><td colspan='4' class='text-center'>No employees found</td></tr>";
                return;
            }

            data.forEach(employee => {
                const row = `<tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.position}</td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="viewEmployee('${employee.id}')">View</button>
                        <button class="btn btn-warning btn-sm" onclick="editEmployee('${employee.id}')">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteEmployee('${employee.id}')">Delete</button>
                    </td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(err => {
            console.error("Error fetching employees", err);
            alert("Failed to load employee data");
        });
}

function viewEmployee(id) {
    fetch(`http://localhost:8080/api/employees/${id}`)
        .then(res => res.json())
        .then(emp => {
            document.getElementById("empId").value = emp.id;
            document.getElementById("empName").value = emp.name;
            document.getElementById("empEmail").value = emp.email;
            document.getElementById("empRole").value = emp.position;
            document.getElementById("empSalary").value = emp.salary;
            currentEmployeeId = emp.id;
            employeeModal.show();
        });
}

function editEmployee(id) {
    viewEmployee(id);
}

function saveEmployee() {
    const id = document.getElementById("empId").value;
    const updated = {
        name: document.getElementById("empName").value,
        email: document.getElementById("empEmail").value,
        position: document.getElementById("empRole").value,
        salary: parseFloat(document.getElementById("empSalary").value),
    };

    fetch(`http://localhost:8080/api/employees/${id}`, {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updated)
    })
        .then(res => res.json())
        .then(data => {
            alert("Employee updated successfully!");
            employeeModal.hide();
            searchEmployees();
        })
        .catch(err => {
            console.error("Update failed", err);
            alert("Failed to update employee.");
        });
}

function deleteEmployee(id) {
    if (confirm("Are you sure you want to delete this employee?")) {
        fetch(`http://localhost:8080/api/employees/${id}`, {
            method: 'DELETE'
        })
        .then(res => {
            if (res.ok) {
                alert("Employee deleted");
                searchEmployees();
            } else {
                alert("Delete failed");
            }
        })
        .catch(err => {
            console.error("Error deleting", err);
        });
    }
}
