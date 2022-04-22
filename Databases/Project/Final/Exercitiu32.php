<html>
    <head>
        <title>Exercitiul 3</title>
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
</style> 
    </head>
    <body>
        <div>
</tr><p>b) Să se găsească detaliile împrumuturilor cu restituire întârziată în ordinea
crescătoare a numărului de zile întârziere.</p>

SELECT id_carte,id_imp,datai,datar,nr_zile,(TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE<br>(datai,'dd-Mon-yyyy') )-nr_zile as "zile intarziate"
<br>FROM Imprumut
<br>WHERE datar IS NOT NULL AND TO_DATE(datar,'dd-Mon-yyyy')-TO_DATE(datai,'dd-Mon-yyyy')>nr_zile
<br>ORDER BY "zile intarziate";
<br><br>
<a href="Exercitiu3b.php" class="button">Cauta</a>
<a href="index.php" class="button">Back</a>
<br><br>
        </div>
    </body>
</html>
