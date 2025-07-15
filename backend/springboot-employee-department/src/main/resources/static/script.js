const API_URL = 'http://localhost:8080/api/employees';

function searchEmployees() {
    const deptId = document.getElementById('deptInput').value.trim();
    if (!deptId) {
        alert('Please enter a Department ID');
        return;
    }

    // Make sure this matches your controller: /api/employees/by-department/{deptId}
    fetch(`${API_URL}/by-department/${deptId}`)
        .then(res => {
            if (!res.ok) {
                throw new Error('No employees found or invalid Department ID');
            }
            return res.json();
        })
        .then(data => {
            populateTable(data);
        })
        .catch(err => {
            console.error('Error fetching employees:', err);
            alert(err.message || 'Failed to fetch employee data.');
            document.getElementById('employeeTableBody').innerHTML = '';
        });
}

function populateTable(employees) {
    const tbody = document.getElementById('employeeTableBody');
    tbody.innerHTML = '';

    employees.forEach(emp => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${emp.id}</td>
            <td>${emp.name}</td>
            <td>${emp.position}</td>
            <td>
                <button onclick='viewEmployee(${JSON.stringify(emp)})'>View</button>
                <button onclick='editEmployee(${JSON.stringify(emp)})'>Edit</button>
                <button onclick='deleteEmployee("${emp.id}")'>Delete</button>
            </td>
        `;

        tbody.appendChild(row);
    });
}

function viewEmployee(emp) {
    setupModal(emp, false);
}

function editEmployee(emp) {
    setupModal(emp, true);
}

function setupModal(emp, editable) {
    document.getElementById('modalTitle').textContent = editable ? 'Edit Employee' : 'Employee Details';
    document.getElementById('modalEmpId').value = emp.id;
    document.getElementById('modalName').value = emp.name;
    document.getElementById('modalPosition').value = emp.position;
    document.getElementById('modalEmail').value = emp.email;

    document.getElementById('modalName').disabled = !editable;
    document.getElementById('modalPosition').disabled = !editable;
    document.getElementById('modalEmail').disabled = !editable;
    document.getElementById('modalSaveBtn').style.display = editable ? 'inline-block' : 'none';

    openModal();
}

function deleteEmployee(empId) {
    if (!confirm('Are you sure you want to delete this employee?')) return;

    fetch(`${API_URL}/${empId}`, { method: 'DELETE' })
        .then(() => {
            alert('Employee deleted');
            searchEmployees();
        })
        .catch(err => console.error('Delete failed:', err));
}

function saveEmployee(event) {
    event.preventDefault();

    const id = document.getElementById('modalEmpId').value;
    const name = document.getElementById('modalName').value;
    const position = document.getElementById('modalPosition').value;
    const email = document.getElementById('modalEmail').value;

    const updatedEmployee = {
        id,
        name,
        position,
        email,
        department: {
            id: document.getElementById('deptInput').value.trim()
        }
    };

    fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedEmployee)
    })
        .then(res => {
            if (!res.ok) throw new Error('Update failed');
            return res.json();
        })
        .then(() => {
            alert('Employee updated successfully!');
            closeModal();
            searchEmployees();
        })
        .catch(err => {
            console.error('Error:', err);
            alert('Failed to update employee.');
        });
}

function openModal() {
    document.getElementById('employeeModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('employeeModal').style.display = 'none';
}

window.onclick = function(event) {
    const modal = document.getElementById('employeeModal');
    if (event.target === modal) closeModal();
};

document.getElementById('modalForm').addEventListener('submit', saveEmployee);
