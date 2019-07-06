<html>
<title>FormularApp</title>
<body>
<div style="width: 800px; height: auto; border: 1px solid black">
<button>Test this</button>
</div>
<h2>Hello World!</h2>
</body>
</html>



<script>
fetch('http://localhost:8080/formularapp/Hello')
.then(response => response.json())
.then(data => {
  console.log(data) // Prints result from `response.json()` in getRequest
})
.catch(error => console.error(error))

</script>