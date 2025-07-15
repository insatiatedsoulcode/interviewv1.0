function searchEmployees() {
  const deptId = document.getElementById('departmentId').value.trim();
  if (!deptId) {
    alert("Please enter a department ID.");
    return;
  }

  fetch(`/api/departments/${deptId}`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Department not found: ${deptId}`);
      }
      return response.json();
    })
    .then(data => {
      const employees = Array.isArray(data.employees) ? data.employees : [];
      const tbody = document.getElementById('employeeTableBody');
      tbody.innerHTML = "";

      if (employees.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="4">No employees found for this department.</td>`;
        tbody.appendChild(row);
        return;
      }

      employees.forEach(emp => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${emp.id}</td>
          <td>${emp.name}</td>
          <td>${emp.position || 'N/A'}</td>
          <td>
            <button onclick="viewEmployee('${emp.id}')">View</button>
            <button onclick="editEmployee('${emp.id}')">Edit</button>
            <button onclick="deleteEmployee('${emp.id}')">Delete</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(error => {
      console.error("Error fetching employees:", error);
      alert("Error: " + error.message);
    });
}

// Dummy handlers for actions
function viewEmployee(id) {
  alert(`View employee: ${id}`);
}
function editEmployee(id) {
  alert(`Edit employee: ${id}`);
}
function deleteEmployee(id) {
  alert(`Delete employee: ${id}`);
}
