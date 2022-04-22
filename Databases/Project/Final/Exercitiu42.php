<html>
    <head>
        <title>Exercitiul 4</title>
        <body bgcolor="lightgray">
        <style>
input[type=text]{
width: 15%;
 padding: 12px 20px;
 margin: 8px 0;
 box-sizing: border-box;
    border-radius: 7px;
color: gray;
}
.button ,input[type=submit]{
    display: inline-block;
    padding: 15px 15px;
    font-size: 20px;
    cursor: pointer;
    text-align: center;
    text-decoration: none;
    color: black;
    background-color: gray;
    border: none;
    border-radius: 12px;
    box-shadow: 0 9px #999;
}

.button:active ,input[type=submit]:active {
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
</style> 
    </head>
    <body>
        <div>
<p>b) Să se găsească perechiile de cărți (id_carte1,id_carte2) de acelasi gen(genul este introdus de catre utilizator)</p>
<form action="Exercitiu4b.php" method=post>
<tr>
<td>Introduceti genul :</td>
<td align="center"><input type="text" name="gen" ></td>
</tr>
<br><br>
<tr>
<td  align="center"><input type=submit value="Cauta"></td>
</tr>
<a href="index.php" class="button">Back</a>
<br><br>
</div>
    </body>
</form>
</html>
