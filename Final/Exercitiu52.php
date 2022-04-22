<html>
    <head>
        <title>Exercitiul 5</title>
        <body bgcolor="lightgray">
        <style>
.button {
    display: inline-block;
    padding: 15px 15px;
    font-size: 20px;
    cursor: pointer;
    text-align: center;
    text-decoration: none;
    color: black;
    background-color:gray;
    border: none;
    border-radius: 12px;
    box-shadow: 0 9px #999;
}


.button:active {
  background-color: white;
  box-shadow: 0 5px #999;
}
div {
           border: 35px double;
            text-align: center;
            border-color: gray;
        }
p {
    font-family: "Chalkduster", Times, serif;
    font-size: 17px;
    text-align:left;
    padding: 30px;

}
th {
    background-color: pink;
    color: black;
    border: 1px solid black;

}
td{

    border-color: gray;
    width: auto;

}
tr{
    text-align: center;
    margin:30px;
    
}

</style> 
    </head>
    <body>
        <div>
<p>b) Să se găsească id-ul cărților cu mai mulți autori.</p>
<br>SELECT DISTINCT (id_carte)
                      <br> FROM Autor a
                      <br> WHERE NOT  (a.id_aut =all (SELECT id_aut FROM Autor  WHERE id_carte=a.id_carte))
                      <br> ORDER BY id_carte;
<br><br>
<br><br>
<a href="Exercitiu5b.php" class="button">Cauta</a>
<a href="index.php" class="button">Back</a>
<br><br>
        </div>
    </body>
</html>
