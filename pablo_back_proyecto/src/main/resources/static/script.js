window.addEventListener('load', function () {

  // Obtener referencia al formulario de agregar paciente
  const formAgregarPaciente = document.getElementById('formAgregarPac');

  // Agregar el evento submit al formulario de agregar paciente
  formAgregarPaciente.addEventListener('submit', function (event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener los valores del formulario
    const formDataPaciente = {
      nombre: document.querySelector('#nombrePaciente').value,
      apellido: document.querySelector('#apellidoPaciente').value,
      dni: document.querySelector('#dniPaciente').value,
      domicilio: {
        calle: document.querySelector('#callePaciente').value,
        numero: document.querySelector('#numeroPaciente').value,
        localidad: document.querySelector('#localidadPaciente').value,
        provincia: document.querySelector('#provinciaPaciente').value
      }
    };

    const url = '/pacientes/registrar';
    const settings = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formDataPaciente)
    };

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        // Mostrar mensaje de éxito
        let successAlert = '<div class="alert alert-success alert-dismissible">' +
          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
          '<strong></strong> Paciente registrado </div>';

        document.querySelector('#response').innerHTML = successAlert;
        document.querySelector('#response').style.display = "block";
        resetUploadForm();
      })
      .catch(error => {
        // Mostrar mensaje de error
        let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
          '<strong> Error, intente nuevamente</strong> </div>';

        document.querySelector('#response').innerHTML = errorAlert;
        document.querySelector('#response').style.display = "block";
        resetUploadForm();
      });
  });

  // Función para resetear el formulario
  function resetUploadForm() {
    document.querySelector('#nombrePaciente').value = "";
    document.querySelector('#apellidoPaciente').value = "";
    document.querySelector('#dniPaciente').value = "";
    document.querySelector('#callePaciente').value = "";
    document.querySelector('#numeroPaciente').value = "";
    document.querySelector('#localidadPaciente').value = "";
    document.querySelector('#provinciaPaciente').value = "";
  }

  // Obtener referencia al formulario de agregar odontólogo
  const formAgregarOdontologo = document.getElementById('formAgregarOdont');

  // Agregar el evento submit al formulario de agregar odontólogo
  formAgregarOdontologo.addEventListener('submit', function (event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener los valores del formulario
    const formDataOdontologo = {
      nombre: document.querySelector('#nombreOdontologo').value,
      apellido: document.querySelector('#apellidoOdontologo').value,
      matricula: document.querySelector('#matriculaOdontologo').value,
    };

    const url = '/odontologos/registrar';
    const settings = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formDataOdontologo)
    };

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        resetUploadForm();
      });
  });

  // Obtener referencia al botón "Listado de turnos"
  const botonListadoTurnos = document.getElementById('listado-turnos');

  // Agregar el evento clic al botón "Listado de turnos"
  botonListadoTurnos.addEventListener('click', function () {
    // Mostrar la tabla de turnos al hacer clic en el botón
    document.getElementById('tabla-turnos').style.display = 'table';
  });

  // Obtener referencia al botón de "Registrar turno"
  const registrarTurnoBtn = document.getElementById('registrar-turno');

  // Asociar evento de clic al botón de "Registrar turno"
  registrarTurnoBtn.addEventListener('click', function () {
    // Redirigir a la página registrarTurno.html
    window.location.href = 'registroTurno.html';
  });

  // Obtener referencia al botón de "Listado de pacientes"
  const listadoPacientesBtn = document.getElementById('listado-pac');

  // Agregar el evento clic al botón "Listado de pacientes"
  listadoPacientesBtn.addEventListener('click', function () {
    // Lógica para acceder al listado de pacientes
    console.log("Accediendo al listado de pacientes...");
  });

  // Obtener referencia al botón de "Listado de odontólogos"
  const listadoOdontologosBtn = document.getElementById('listado-odont');

  // Agregar el evento clic al botón "Listado de odontólogos"
  listadoOdontologosBtn.addEventListener('click', function () {
    // Lógica para acceder al listado de odontólogos
    console.log("Accediendo al listado de odontólogos...");
  });

  // Función para mostrar el listado de turnos
  function mostrarListadoTurnos() {
    fetch('/turnos')
      .then(response => response.json())
      .then(data => {
        const listaTurnosBody = document.getElementById('lista-turnos-body');
        listaTurnosBody.innerHTML = '';

        data.forEach(turno => {
          const filaTurno = document.createElement('tr');

          const celdaPaciente = document.createElement('td');
          celdaPaciente.textContent = turno.paciente;
          filaTurno.appendChild(celdaPaciente);

          const celdaOdontologo = document.createElement('td');
          celdaOdontologo.textContent = turno.odontologo;
          filaTurno.appendChild(celdaOdontologo);

          const celdaFechaHora = document.createElement('td');
          if (turno.fecha) {
            const fechaHora = new Date(turno.fecha);
            if (!isNaN(fechaHora)) {
              celdaFechaHora.textContent = fechaHora.toLocaleString();
            } else {
              celdaFechaHora.textContent = "Fecha y hora inválida";
            }
          } else {
            celdaFechaHora.textContent = "Fecha y hora no especificada";
          }
          filaTurno.appendChild(celdaFechaHora);

          listaTurnosBody.appendChild(filaTurno);
        });
      })
      .catch(error => {
        console.error('Error al obtener el listado de turnos:', error);
      });
  }

  // Agregar el evento clic al botón "Listado de turnos"
  const listadoTurnosBtn = document.getElementById('listado-turnos');
  listadoTurnosBtn.addEventListener('click', mostrarListadoTurnos);

});
