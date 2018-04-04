# DAM-ImproMusic

Proyecto final para el CFGS en Desarrollo de Aplicaciones Multiplataforma (DAM)

Con esta aplicación Android intentaré ayudar a músicos en su experiencia practicando en casa, incluyendo bases musicales y la opción de poder
participar en retos junto a otros usuarios para aportarse entre todos en el mundo de la improvisación.

# Análisis de las áreas

#### Vamos a encontrar distintas áreas de Gestión en mi Aplicación:

1. [Gestión de usuarios](#Área-de-gestión-de-usuarios)

2. [Gestión de canciones/bases musicales](#Área-de-gestión-de-cancionesbases-musicales)

3. [Gestión de retos](#Área-de-gestión-de-retos)

4. [Gestión de votaciones](#Área-de-gestión-de-votaciones)

5. [Gestión de comentarios](#Área-de-gestión-de-comentarios)

6. [Gestión de ganadores](#Área-de-gestión-de-ganadores)

A continuación, procedo a un análisis algo más exhaustivo de cada una.

### Área de gestión de usuarios

&nbsp;&nbsp;&nbsp;&nbsp;**Incluye los puntos 3 y 4 del proyecto.**

* Finalidad: Conservar las preferencias básicas que cada usuario pueda guardar. Diferenciar entre distintos tipos de usuario.
Perfil básico de usuario. Lista de retos en los que participa.

* Elementos: 
	- Usuario

		- RD1: Nombre de Usuario
		- RD2: Preferencias
		- RD3: Foto de perfil
		- RD4: Fecha Registro
		- RD5: Tipo de Usuario
	- Reto

		- RD1: Nombre del Reto

---

### Área de gestión de canciones/bases musicales:

* Finalidad: Subir canciones, eliminar canciones, observar los retos vinculados a la misma.

* Elementos: 
	- Usuario
		- RD1: Nombre de Usuario
	- Canciones:
		- RD1: Nombre de la canción
		- RD2: Artista
		- RD3: Álbum
		- RD4: Género
		- RD5: Duración
  
---  

### Área de gestión de retos:

* Finalidad: Creación de retos, unirse a retos, dar reto por terminado, cerrar reto.

* Elementos:
	- Usuario
		- RD1: Nombre de Usuario
	- Reto
		- RD1: Nombre del Reto
		- RD2: Fecha de creación del Reto
		- RD3: Fecha de finalización del Reto
		- RD4: Normativa
	- Canción: 
		- RD1: Nombre de la canción

---
  
### Área de gestión de votaciones:

* Finalidad: Votar un reto. Observar votaciones del resto de usuarios.

* Elementos: 
	- Usuario: 
		- RD1: Nombre de Usuario
	- Reto: 
		- RD1: Nombre del Reto
	- Votación: 
		- RD1: Fecha de votación

	**Nota: Cada usuario podrá votar sólo una vez.**	

---

### Área de gestión de comentarios:

* Finalidad: Comentar la participación de otros usuarios en un reto

* Elementos: 
	- Usuario:
		- RD1: Nombre de Usuario
	- Reto:
		- RD1: Nombre del Reto
	- Comentario: 
		- RD1: Fecha
		- RD2: Contenido

---

### Área de gestión de ganadores:

* Finalidad: Marcar usuarios como ganadores de un reto.

* Elementos:
	- Usuario: 
		- RD1: Nombre de Usuario
	- Reto:
		- RD2: Nombre del Reto

---

# Entidad-Relación

### Modelo Entidad-Relación de mi proyecto:

![imagen](https://i.imgur.com/heTBqyy.png)

