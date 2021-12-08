<!-- Draw a blank map -->
let c = $("canvas")[0].getContext("2d");
c.font = "20px Arial";

if (type >= 2){
    if (type == 2) {
        c.fillText("AB",190,35);
    }
    c.fillText("00", 115, 85);
    c.fillText("01", 165, 85);
    c.fillText("11", 215, 85);
    c.fillText("10", 265, 85);
    c.moveTo(50,50); c.lineTo(50,150);
    c.moveTo(100,50); c.lineTo(100,150);
    c.moveTo(150,50); c.lineTo(150,150);
    c.moveTo(200,50); c.lineTo(200,150);
    c.moveTo(250,50); c.lineTo(250,150);
    c.moveTo(300,50); c.lineTo(300,150);
    c.moveTo(50,50); c.lineTo(300,50);
    c.moveTo(50,100); c.lineTo(300,100);
    c.moveTo(50,150); c.lineTo(300,150);
    c.stroke();
    if (type >= 3) {
        if (type == 3) {
            c.fillText("BC",190,35);
            c.fillText("A",15,160);
            c.fillText("0",65,135);
            c.fillText("1",65,185);
        }
        c.moveTo(50,150); c.lineTo(50,200);
        c.moveTo(100,150); c.lineTo(100,200);
        c.moveTo(150,150); c.lineTo(150,200);
        c.moveTo(200,150); c.lineTo(200,200);
        c.moveTo(250,150); c.lineTo(250,200);
        c.moveTo(300,150); c.lineTo(300,200);
        c.moveTo(50,200); c.lineTo(300,200);
        c.stroke();
        if (type == 4) {
            c.fillText("CD",190,35);
            c.fillText("AB",15,210);
            c.fillText("00",65,135);
            c.fillText("01",65,185);
            c.fillText("11", 65, 235);
            c.fillText("10", 65, 285);
            c.moveTo(50, 200); c.lineTo(50, 300);
            c.moveTo(100, 200); c.lineTo(100, 300);
            c.moveTo(150, 200); c.lineTo(150, 300);
            c.moveTo(200, 200); c.lineTo(200, 300);
            c.moveTo(250,200); c.lineTo(250,300);
            c.moveTo(300,200); c.lineTo(300,300);
            c.moveTo(50, 250); c.lineTo(300, 250);
            c.moveTo(50, 300); c.lineTo(300, 300);
            c.stroke();
        }
    }
}
<!-- Add function's binary values -->
for (let i = 0; i < values.length; i++) {
    let cell = JSON.parse(JSON.stringify(values[i]));
    let x = cell.x;
    let y = cell.y;
    c.fillText(cell.value,x * 50 + 115, y * 50 + 135);
}
<!-- Draw box around minterm -->
for (let i = 0; i < areas.length; i++) {
    let color = "#" + Math.floor((i+1)*1677721).toString(16);;

    c.beginPath();
    for (let j = 0; j < areas[i].length; j++) {
        let area = JSON.parse(JSON.stringify(areas[i][j]));
        c.strokeStyle = color;
        c.moveTo(area.begin.x * 50 + 110, area.begin.y * 50 + 110);
        c.lineTo(area.begin.x * 50 + 110, area.end.y * 50 + 140);
        c.lineTo(area.end.x * 50 + 140, area.end.y * 50 + 140);
        c.lineTo(area.end.x * 50 + 140, area.begin.y * 50 + 110);
        c.lineTo(area.begin.x * 50 + 110, area.begin.y * 50 + 110);
        c.stroke();
    }
    $(document).ready(function(){
        $(".minterm-color").eq(i).css("background-color",color);
    })
}
