           var mapObj;
		   var MDrag;
		// 起、终点
		      
		var start_xy = new AMap.LngLat(118.931441,32.116608);
        var end_xy ;
		   
          // 加载地图
          function mapInit(){
    	  mapObj = new AMap.Map('map',{
          zoom: 16,
		  resizeEnable: true,
          center: [118.93052101,32.11358562]
		  
		  
          }); 
		  mapObj.setDefaultCursor("crosshair");
		  // scale
		  // AMap.plugin(['AMap.ToolBar','AMap.Scale'],function(){
           // })
            AMap.plugin(['AMap.ToolBar','AMap.Scale'],function(){
             var toolBar = new AMap.ToolBar();
             var scale = new AMap.Scale();
             mapObj.addControl(toolBar);
             mapObj.addControl(scale);
            })
		AMap.event.addListener(mapObj,'click',getLnglat); // 点击事件
		
		// 右键菜单

  var contextMenu = new AMap.ContextMenu();  // 创建右键菜单
    // 右键放大
    contextMenu.addItem("放大一级", function() {
        mapObj.zoomIn();
    }, 0);
    // 右键缩小
    contextMenu.addItem("缩小一级", function() {
        mapObj.zoomOut();
    }, 1);
    // 右键重新加载地图
    contextMenu.addItem("重新加载地图", function(e) {
        mapObj.setZoomAndCenter(16, [118.93052101,32.11358562]);
		clearMap();
    }, 2);
    // 右键添加Marker标记
    contextMenu.addItem("添加标记", function(e) {
        var marker = new AMap.Marker({
            map: mapObj,
			draggable: true,  // 是否可拖
            position: contextMenuPositon // 基点位置
			
			
        });
		end_xy= contextMenuPositon; // 标记点位置赋值给终点
		marker.setAnimation('AMAP_ANIMATION_BOUNCE'); // 标记动画
    }, 3);
     
    // 地图绑定鼠标右击事件——弹出右键菜单
    mapObj.on('rightclick', function(e) {
        contextMenu.open(mapObj, e.lnglat);
        contextMenuPositon = e.lnglat;
    });
		}   
        // 当前定位
		 
		function position_now(){
			clearMap();
			var marker = new AMap.Marker({
            map: mapObj,
            position:end_xy// 基点位置
			
			});
			
			marker.setAnimation('AMAP_ANIMATION_BOUNCE'); // 标记动画
			}
		    
		// 鼠标点击，获取经纬度坐标
		function getLnglat(e){
         var x = e.lnglat.getLng();
         var y = e.lnglat.getLat();
        document.getElementById("lnglats").innerHTML = x + "," + y;
		}
		// 清空地图
		function clearMap(){
         mapObj.clearMap();
		document.getElementById("panel").innerHTML = '&nbsp;'; 
        document.getElementById("lnglats").innerHTML = '&nbsp;';
		}
        

		
	
		function walking_route() {  
		AMap.service('AMap.Walking',function(){// 回调函数
        // 实例化Walking
        // walking= new AMap.Walking({city: '北京市'});
        // TODO: 使用walking对象调用步行路径规划相关的功能
        })
		// 步行导航
        var walking = new AMap.Walking({
        map: mapObj,
        panel: "panel"
    }); 
    // 根据起终点坐标规划步行路线
    walking.search(start_xy,end_xy,function(status, result){});
		}
		
		// 可拖拽的驾车导航
function driving_route_drag(){
    clearMap();
    var path = [start_xy,end_xy];
    mapObj.plugin("AMap.DragRoute",function(){
        MDrag = new AMap.DragRoute(mapObj, path, AMap.DrivingPolicy.LEAST_FEE); // 构造拖拽导航类
        MDrag.search(); // 查询导航路径并开启拖拽导航
    });
}

