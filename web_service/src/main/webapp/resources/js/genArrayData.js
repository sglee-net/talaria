function genArrayData(json) {
	var arrayData = [];
	var obj = JSON.parse(json);
	for (var intCount = 0; intCount < obj.length; intCount++) {
        var curItem = [obj[intCount].ts, obj[intCount].x, obj[intCount].y];
        arrayData[arrayData.length] = curItem;
    }
	
	return arrayData;
}
