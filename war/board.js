
//Functions
	var allowedBoard = 9;
			function initialize(){
                var canvas = document.getElementById("canvas_1");
                //Draws entire frame around box
                drawBigBoard();
                highlightBoard(allowedBoard);
                //Will draw the individual boards, indexed starting at 0 meaning left,top 2 meaning right bottom
                drawMiniBoards();    	
				canvas.addEventListener("mousedown", doMouseDown, false);
			}
            
            //This registers the mouse listener
			function doMouseDown(event){
				doMouseDown.user = doMouseDown.user || 0;
                var canvas = document.getElementById("canvas_1");
                var rect = canvas.getBoundingClientRect();
                
                //Used for math manipulation
                var starting_corner = 25;
                var bigboard_width = 150;
                var smallboard_width = 50;
                
				canvas_x = event.clientX - rect.left;
				canvas_y = event.clientY - rect.top;
				//alert("X=" + canvas_x + " Y=" + canvas_y);
                
                //Now we will check to see where the position was so that we can draw a rectangle
                canvas_x -= starting_corner; //This will make our division easier
                canvas_y -= starting_corner;
                
                //Now we know that canvas_x and canvas_y divided by 150 will give us what quadrant we are in
                quadrant_x = Math.floor(canvas_x/bigboard_width);   //We can use floor since only positive
                quadrant_y = Math.floor(canvas_y/bigboard_width);
                
                //Now we will check to see if this was in the right board (highlighted one)
                bigboard_index = quadrant_y * 3 + quadrant_x;
                if(allowedBoard != 9 && bigboard_index != allowedBoard){
                	//This means that the user tried to click in a different board than allowed
                	alert("Sorry, please play in the highlighted board, correct board is " + allowedBoard);
                	return;
                }
                
                //Within the quadrant, we want to know the relative location. We can use mod to do this
                miniboardquadrant_x = Math.floor((canvas_x % bigboard_width) / smallboard_width);
                miniboardquadrant_y = Math.floor((canvas_y % bigboard_width) / smallboard_width);
                //alert("mini_X=" + miniboardquadrant_x + " mini_Y=" + miniboardquadrant_y);
                
                bigboard_coordinate = quadrant_y * 3 + quadrant_x; //that will make (2,1) into 5
                smallboard_coordinate = miniboardquadrant_y * 3 + miniboardquadrant_x;
                var user_id = (doMouseDown.user % 2) + 1;
                
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function() 
                {
                	if(xhr.readyState == 4) //Means that the response was sent successfully
                	{
                        var data = xhr.responseText;
                        var splitData = data.split(" ");
                        if(splitData[0] == "true")
                        {
                        	//The move is valid and we should fill in the miniboard
                            var highlight_index = splitData[1];
                            allowedBoard = highlight_index;
                            repaint();
                            doMouseDown.user++;
                            document.getElementById('boldStuff').innerHTML = user_id + " " + bigboard_coordinate + " " + smallboard_coordinate;
                        }
                        else{
                        	alert("This space is already occupied");
                        }
                        //the winner part. 
                        if(splitData[2] == "true")
                        {
                        	//redirects to show the winning message. 
                            window.location.href = "winpage.jsp?user=" + user_id;
                        	 
                        }

                	}
                }
                xhr.open("POST", "projectpage", false);
               xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
              xhr.send("bboard=" + bigboard_coordinate + "&lboard=" + smallboard_coordinate + "&user="+user_id);
          	alert("Testing");
               // xhr.send(null);                
			}
            
            function drawBigBoard(){
                var canvas = document.getElementById("canvas_1");
                var ctx = canvas.getContext("2d");
            
                //Variables I create to draw board
                var starting_corner = 25;
                var ending_corner = 475;
                var bigboard_width = 150;
                var smallboard_width = 50;
                var smallboard_offset = 10;
                
                ctx.strokeStyle="#000000"; //all black
                ctx.lineWidth = 5; //Make it a little bolder
                //Draw the entire frame of the box
                ctx.moveTo(starting_corner,starting_corner);
                ctx.lineTo(ending_corner,starting_corner);
                ctx.stroke();
                ctx.lineTo(ending_corner,ending_corner);
                ctx.stroke();
                ctx.lineTo(starting_corner,ending_corner);
                ctx.stroke();
                ctx.lineTo(starting_corner,starting_corner);
                ctx.stroke();

                //Draw the 2 big lines down the board, width is 150
                ctx.moveTo(starting_corner + bigboard_width, starting_corner);
                ctx.lineTo(starting_corner + bigboard_width, ending_corner);
                ctx.stroke();
                ctx.moveTo(starting_corner + 2 * bigboard_width, starting_corner);
                ctx.lineTo(starting_corner + 2 * bigboard_width, ending_corner);
                ctx.stroke();

                //Draw the 2 big lines across the board, width is 150
                ctx.moveTo(starting_corner, starting_corner  + bigboard_width);
                ctx.lineTo(ending_corner, starting_corner + bigboard_width);
                ctx.stroke();
                ctx.moveTo(starting_corner, starting_corner  + 2 * bigboard_width);
                ctx.lineTo(ending_corner, starting_corner + 2 * bigboard_width);
                ctx.stroke();
	       }
            
            	//We will break up the large board into 9 sections, top left corner is 0,0 and bottom right is 2,2
            function drawMiniBoardAtPosition(x, y){
            	var c = document.getElementById("canvas_1");
            	var ctx = c.getContext("2d");

            	var starting_corner = 25;
            	var ending_corner = 475;
            	var bigboard_width = 150;
            	var smallboard_width = 50;
            	var smallboard_offset = 10;
            	ctx.strokeStyle="#000000"; //all black
                ctx.lineWidth = 1;
                //Draw the first smaller board in the upper, left corner
                ctx.moveTo(starting_corner + smallboard_width + (x * bigboard_width), 
                           starting_corner + smallboard_offset  + (y * bigboard_width));
                ctx.lineTo(starting_corner + smallboard_width + (x * bigboard_width), 
                           starting_corner + 3 * smallboard_width - smallboard_offset + (y * bigboard_width));
                ctx.stroke();
                ctx.moveTo(starting_corner + 2 * smallboard_width + (x * bigboard_width), 
                           starting_corner + smallboard_offset + (y * bigboard_width));
                ctx.lineTo(starting_corner + 2 * smallboard_width + (x * bigboard_width), 
                           starting_corner + 3 * smallboard_width - smallboard_offset + (y * bigboard_width));
                ctx.stroke();
                ctx.moveTo(starting_corner + smallboard_offset + (x * bigboard_width), 
                           starting_corner + smallboard_width + (y * bigboard_width));
                ctx.lineTo(starting_corner + 3 * smallboard_width - smallboard_offset + (x * bigboard_width), 
                           starting_corner + smallboard_width + (y * bigboard_width));
                ctx.stroke();
                ctx.moveTo(starting_corner + smallboard_offset + (x * bigboard_width), 
                           starting_corner + 2 * smallboard_width + (y * bigboard_width));
                ctx.lineTo(starting_corner + 3 * smallboard_width - smallboard_offset + (x * bigboard_width), 
                           starting_corner + 2 * smallboard_width + (y * bigboard_width));
                ctx.stroke();
	       }

	       function drawMiniBoards(){
	       	    drawMiniBoardAtPosition(0,0);
                drawMiniBoardAtPosition(1,0);
                drawMiniBoardAtPosition(2,0);
                drawMiniBoardAtPosition(0,1);
                drawMiniBoardAtPosition(1,1);
                drawMiniBoardAtPosition(2,1);
                drawMiniBoardAtPosition(0,2);
                drawMiniBoardAtPosition(1,2);
                drawMiniBoardAtPosition(2,2);
	       }
	       
       		function fillInMiniBoardInQuadrantAndPosition(quadrant_x, quadrant_y, miniboard_x, miniboard_y, count){
                var canvas = document.getElementById("canvas_1");
                var ctx = canvas.getContext("2d");
            
                //Variables I create to draw board
                var starting_corner = 25;
                var ending_corner = 475;
                var bigboard_width = 150;
                var smallboard_width = 50;
                var smallboard_offset = 10;
                var fill_offset = 5;
                
                //First, let's move to the right spot. We have the big board quadrant and little board quadrant
                fillInX = starting_corner + quadrant_x * bigboard_width + miniboard_x * smallboard_width;
                fillInY = starting_corner + quadrant_y * bigboard_width + miniboard_y * smallboard_width;
                /*
                ctx.fillStyle="#FF0000";    //makes the color red
                ctx.fillRect(fillInX + fill_offset,fillInY + fill_offset, smallboard_width - smallboard_offset, 
                             smallboard_width - smallboard_offset); 
            
                */
                
                if(count == 1){
                	var x_image = document.getElementById("tic-tac-x");
                	ctx.drawImage(x_image, fillInX + fill_offset, fillInY + fill_offset, 40, 40);
                }
                else{
                	var o_image = document.getElementById("tic-tac-o");
                	ctx.drawImage(o_image, fillInX + fill_offset, fillInY + fill_offset, 40, 40);
                }
            }
            
            function highlightBoard(index){
            	//The index is the index of the big board. 9 means that we color it all
            	if(index == 9){
            		//Highlight the entire board
            		highlightMiniBoardAtPosition(0,0);
            		highlightMiniBoardAtPosition(0,1);
            		highlightMiniBoardAtPosition(0,2);
            		highlightMiniBoardAtPosition(1,0);
            		highlightMiniBoardAtPosition(1,1);
            		highlightMiniBoardAtPosition(1,2);
            		highlightMiniBoardAtPosition(2,0);
            		highlightMiniBoardAtPosition(2,1);
            		highlightMiniBoardAtPosition(2,2);    		
            	}
            	else{
            		var y = Math.floor(index / 3);
            		var x = index % 3;
            		highlightMiniBoardAtPosition(x,y);
            	}
            }
            
            function highlightMiniBoardAtPosition(x, y){
                var c = document.getElementById("canvas_1");
                var ctx = c.getContext("2d");

                var starting_corner = 25;
                var ending_corner = 475;
                var bigboard_width = 150;
                var smallboard_width = 50;
                var smallboard_offset = 10;
                
                ctx.fillStyle="#ffff00"; //yellow
                ctx.fillRect(starting_corner + x * bigboard_width + smallboard_offset, starting_corner + y * bigboard_width + smallboard_offset, bigboard_width-20, bigboard_width-20);
    	    }
            
            function repaint(){   	
            	//First highlight
                var c = document.getElementById("canvas_1");
                var ctx = c.getContext("2d");     
                ctx.clearRect(0,0,c.width, c.height); //Clears the whole screen. Hard coded from .jsp file
                ctx.beginPath(); //this makes sure we don't have a bunch of other lines
                
                drawBigBoard();                
                highlightBoard(allowedBoard);
                
                //Then put any completed boards images if it highlights
                var req = new XMLHttpRequest();
                req.onreadystatechange = function() 
                {
                	if(req.readyState == 4) //Means that the response was sent successfully
                	{
                        var completedBoardsString = req.responseText;
                        var completedBoardsSplit = completedBoardsString.split(" ");
                        var bigx = document.getElementById("big-x");
                        var bigo = document.getElementById("big-o");
                        var k;
                        for(k = 0; k < 9; k++){
                        	if(completedBoardsSplit[k] == "1"){
                        		//Draw a big X
                        		drawBigImageAtBigBoard(k, bigx);
                        	}
                        	else if( completedBoardsSplit[k] == "2"){
                        		//Draw a big o
                        		drawBigImageAtBigBoard(k, bigo);
                        	}
                        	else{
                        		//Do nothing, maybe an error condition
                        	}
                        }
                       
                    }
                }                    
                req.open("GET", "completedboards", false);
                req.send();
            	
            	//Then draw lines
            	drawMiniBoards();
            	
            	
            	//Then fill the mini images in
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function() 
                {
                	if(xhr.readyState == 4) //Means that the response was sent successfully
                	{
                        var data = xhr.responseText;
                        var splitData = data.split(" ");
                        var x_image = document.getElementById("tic-tac-x");
                    	var o_image = document.getElementById("tic-tac-o");
                    	var counter = 0;
                    	var i, j;
                    	for(i = 0; i < 9; i++){
                    		for(j = 0; j < 9; j++){
                    			if(splitData[counter] == "1"){
                    				//This means that we are drawing an x
                    				fillImageAtBigBoardLittleBoard(i, j, x_image);
                    			}
                    			else if(splitData[counter] == "2"){
                    				//This means that we are drawing an o
                    				fillImageAtBigBoardLittleBoard(i,j,o_image);
                    			}
                    			else{
                    				//This means that there is nothing in the square, do nothing
                    			}
                    			counter++;		
                    		}
                    	}                    
                	}
                }
                xhr.open("GET", "projectpage", false);
                xhr.send();
            }
            
            function fillImageAtBigBoardLittleBoard(bigboard, littleboard, image){
                var canvas = document.getElementById("canvas_1");
                var ctx = canvas.getContext("2d");
            
                //Variables I create to draw board
                var starting_corner = 25;
                var ending_corner = 475;
                var bigboard_width = 150;
                var smallboard_width = 50;
                var smallboard_offset = 10;
                var fill_offset = 5;
                
                var quadrant_x = bigboard % 3;
                var quadrant_y = Math.floor(bigboard / 3);
                var miniboard_x = littleboard % 3;
                var miniboard_y = Math.floor(littleboard / 3);
                
                //First, let's move to the right spot. We have the big board quadrant and little board quadrant
                fillInX = starting_corner + quadrant_x * bigboard_width + miniboard_x * smallboard_width;
                fillInY = starting_corner + quadrant_y * bigboard_width + miniboard_y * smallboard_width;
            	ctx.drawImage(image, fillInX + fill_offset, fillInY + fill_offset, 40, 40);
            }
            
            function drawBigImageAtBigBoard(bigboard, image){
                var canvas = document.getElementById("canvas_1");
                var ctx = canvas.getContext("2d");
            
                //Variables I create to draw board
                var starting_corner = 25;
                var ending_corner = 475;
                var bigboard_width = 150;
                var smallboard_width = 50;
                var smallboard_offset = 10;
                var fill_offset = 5;
                
                var quadrant_x = bigboard % 3;
                var quadrant_y = Math.floor(bigboard / 3);
                
                fillInX = starting_corner + quadrant_x * bigboard_width + 25;
                fillInY = starting_corner + quadrant_y * bigboard_width + 25;
                ctx.drawImage(image, fillInX, fillInY, 100,100);
            	
            }
     
            
                
                