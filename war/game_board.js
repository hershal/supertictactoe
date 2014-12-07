/* The top left is the "x,y" reference corner */
var cell = function(x, y, width, height) {

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    /* Occupant is either 'x' or 'o' */
    this.occupant = null;
    this.hover = null;
}

cell.prototype.set_occupant = function(occupant) {

    this.occupant = occupant;
}

cell.prototype.set_hover = function(hover) {
    this.hover = hover;
}

cell.prototype.stroke_box = function(ctx) {

    ctx.beginPath();
    ctx.globalAlpha = 1.0;
    ctx.strokeStyle="#e0e0e0";
    ctx.lineWidth = 1;
    ctx.rect(this.x, this.y, this.width, this.height);
    ctx.stroke();
}

cell.prototype._fill_bg = function(ctx, color, alpha) {

    ctx.beginPath();
    ctx.globalAlpha = alpha;
    ctx.fillStyle=color;
    ctx.fillRect(this.x, this.y, this.width, this.height);
    ctx.fill();
}

cell.prototype.fill_box = function(ctx) {

    var k_o_fill_color = "#ff9800";
    var k_x_fill_color = "#2196f3";
    var k_null_fill_color = "#ffffff";

    var k_hover_alpha = 0.1;
    var k_fill_alpha = 0.5;

    if (this.occupant == "o") {
        this._fill_bg(ctx, k_o_fill_color, k_fill_alpha);
    } else if (this.occupant == "x") {
        this._fill_bg(ctx, k_x_fill_color, k_fill_alpha);
    } else {
        if (this.hover) {
            this._fill_bg(ctx, k_x_fill_color, k_hover_alpha);
        } else {
            this._fill_bg(ctx, k_null_fill_color, 1.0);
        }
    }
}

cell.prototype.clear = function(ctx) {

    ctx.clearRect(this.x, this.y, this.width, this.height);
}

cell.prototype.draw = function(ctx) {

    this.clear(ctx);
    this.fill_box(ctx);
    this.stroke_box(ctx);
}

cell.prototype.contains = function(x, y) {
    if (((x > this.x) && (x < (this.x + this.width))) &&
        ((y > this.y) && (y < (this.y + this.height)))) {
        return true;
    }
    return false;
}

var board = function(x, y, width, height) {

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    /* A TicTacToe board implicitly has only 3 rows and columns */
    this.rows = 3;
    this.cols = 3;

    this.cells = {};

    var width_delta = this.width/this.cols;
    var height_delta = this.height/this.rows;

    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            /* this.cells[i][j] = */
            /*     new cell(i*x, j*y, width_delta, height_delta); */
            this.cells[i*this.rows + j] =
                new cell(j*width_delta + this.x, i*height_delta + this.y,
                         width_delta, height_delta);
        }
    }
}

board.prototype.draw = function(ctx) {

    ctx.beginPath();
    ctx.globalAlpha = 1.0;
    ctx.strokeStyle="#e0e0e0";
    ctx.lineWidth = 5;
    ctx.rect(this.x, this.y,
             this.width, this.height);
    ctx.stroke();

    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            this.cells[i * this.rows + j].draw(ctx);
        }
    }
}

board.prototype.getCellAt = function(x,y) {
    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            if (this.cells[i*this.rows + j].contains(x,y)) {
                return {id: i*this.rows + j,
                        val: this.cells[i*this.rows + j]};
            }
        }
    }
    return null;
}

board.prototype.getCell = function(id) {
    if (id < (this.rows*this.cols)) {
        return this.cells[id];
    }
    return null;
}

var superboard = function(x, y, width, height) {

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    /* A TicTacToe board implicitly has only 3 rows and columns */
    this.rows = 3;
    this.cols = 3;

    var width_delta = this.width/this.cols;
    var height_delta = this.height/this.rows;

    this.boards = {};

    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            this.boards[i*this.rows + j] =
                new board(i*width_delta + this.x, j*height_delta + this.y,
                          width_delta, height_delta);
        }
    }
}

superboard.prototype.draw = function(ctx) {

    var width_delta = this.width/this.cols;
    var height_delta = this.height/this.rows;

    ctx.beginPath();
    ctx.globalAlpha = 1.0;
    ctx.strokeStyle="#e0e0e0";
    ctx.lineWidth = 5;
    ctx.rect(this.x, this.y, this.width, this.height);
    /* ctx.shadowColor = '#999'; */
    /* ctx.shadowBlur = 4; */
    ctx.stroke();

    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            this.boards[i * this.rows + j].draw(ctx);
        }
    }
}

superboard.prototype.getCellAt = function(x,y) {

    for (var i=0; i<this.rows; ++i) {
        for (var j=0; j<this.cols; ++j) {
            var cellAt = this.boards[i*this.cols + j].getCellAt(x,y);
            if (cellAt != null) {
                return {id_outer: i*this.rows + j,
                        id_inner: cellAt.id,
                        val: cellAt.val};
            }
        }
    }
}

superboard.prototype.getCell = function(id_outer, id_inner) {

    if (id_outer < (this.rows*this.cols)) {
        return this.boards[id_outer].getCell(id_inner);
    }
    return null;
}

function getMousePos(cnv, evt) {
    var rect = cnv.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}
