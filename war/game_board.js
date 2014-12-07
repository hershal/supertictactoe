k_o_fill_color = "#ff9800";
k_x_fill_color = "#2196f3";
k_hover_fill_color = "#aaaaaa";
k_null_fill_color = "#ffffff";

k_hover_alpha = 0.1;
k_fill_alpha = 1.0
k_win_alpha = 0.85;
k_null_alpha = 1.0;

function draw_player_glyph(ctx, player, center_x, center_y, inner_radius) {

    if (player == null) { return; }
    else if (player == "x") {
        /* Draw the X */
        draw_x(ctx, center_x, center_y, inner_radius)
    } else if (player == "o") {
        /* Draw the O */
        draw_o(ctx, center_x, center_y, inner_radius)
    }
}

function draw_x(ctx, center_x, center_y, inner_radius) {

    ctx.beginPath();
    ctx.strokeStyle = 'white';
    ctx.moveTo(center_x - inner_radius, center_y - inner_radius);
    ctx.lineTo(center_x + inner_radius, center_y + inner_radius);
    ctx.moveTo(center_x - inner_radius, center_y + inner_radius);
    ctx.lineTo(center_x + inner_radius, center_y - inner_radius);
    ctx.stroke();
}

function draw_o(ctx, center_x, center_y, inner_radius) {

    ctx.beginPath();
    ctx.strokeStyle = 'white';
    ctx.arc(center_x, center_y, inner_radius, 0, Math.PI * 2, false);
    ctx.stroke();
}

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
    ctx.strokeStyle = "#e0e0e0";
    ctx.lineWidth = 1;
    ctx.rect(this.x, this.y, this.width, this.height);
    ctx.stroke();
}

cell.prototype._fill_bg = function(ctx, color, alpha) {

    ctx.beginPath();
    ctx.globalAlpha = alpha;
    ctx.fillStyle = color;
    ctx.fillRect(this.x, this.y, this.width, this.height);
    ctx.fill();
}

cell.prototype.fill_box = function(ctx) {

    if (this.occupant == "o") {
        this._fill_bg(ctx, k_o_fill_color, k_fill_alpha);
    } else if (this.occupant == "x") {
        this._fill_bg(ctx, k_x_fill_color, k_fill_alpha);
    } else {
        if (this.hover) {
            this._fill_bg(ctx, k_hover_fill_color, k_hover_alpha);
        } else {
            this._fill_bg(ctx, k_null_fill_color, k_null_alpha);
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
    var inner_radius = this.height > this.width ? this.width*1/4 : this.height*1/4;
    ctx.lineWidth = inner_radius/2;
    draw_player_glyph(ctx, this.occupant,
                      this.x + this.width/2, this.y + this.height/2, inner_radius);
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

    this.winner = null;

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

board.prototype.draw_winner = function(ctx) {

    var color = null;
    if (this.winner == null) { return; }
    else if (this.winner == "x") {
        color = k_x_fill_color;
    } else if (this.winner == "o") {
        color = k_o_fill_color;
    }

    var center_x = this.x + this.width/2;
    var center_y = this.y + this.height/2;

    var overlay_radius = this.height > this.width ? this.width*2/5 : this.height*2/5;
    var inner_radius = overlay_radius*2/5;

    ctx.save();
    ctx.beginPath();
    ctx.globalAlpha = k_win_alpha;
    ctx.fillStyle = color;
    ctx.arc(center_x, center_y, overlay_radius, 0, Math.PI * 2, false);
    /* ctx.closePath(); */
    ctx.fill();

    ctx.beginPath();
    ctx.arc(center_x, center_y, overlay_radius, 0, Math.PI * 2, false);
    ctx.clip();

    ctx.beginPath();
    ctx.strokeStyle = 'white';
    ctx.lineWidth = 5;
    ctx.shadowBlur = 30;
    ctx.shadowColor = 'white';
    ctx.shadowOffsetX = 0;
    ctx.shadowOffsetY = 0;
    ctx.arc(center_x, center_y, overlay_radius+3, 0, Math.PI * 2, false);
    ctx.stroke();

    draw_player_glyph(ctx, this.winner, center_x, center_y, inner_radius);
    ctx.restore();
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

    this.draw_winner(ctx);
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
