package gameData;

import java.awt.Graphics;
import java.util.LinkedList;

import singleton.GameProperties;

/***********************************************************************************
 * @author:	Marvin Hofmann	Klasse: DQI10	*
 * Prog.Name: Chunk.java	Beschreibung	*
 * Version:	1.0	Datum: 01.01.2013	*
 * Compiler:	Oracle Java OS: Microsoft Windows 7	*
 * **********************************************************************************/
public class Chunk {

	Block[][] blocks = new Block[GameProperties.MAP_SIZE_CHUNK][GameProperties.MAP_SIZE_CHUNK];
	int xpos, ypos;
	boolean visible = true;
	boolean dummy = false;

	public Chunk(int posx, int posy){
		this.xpos = posx;
		this.ypos = posy;
		dummy = true;
	}

	public void updatePosition(){
		if(!dummy){
			double px = (GameProperties.playerx/GameProperties.GRAPHICS_SIZE_BLOCK)/GameProperties.MAP_SIZE_CHUNK;
			double py = (GameProperties.playery/GameProperties.GRAPHICS_SIZE_BLOCK)/GameProperties.MAP_SIZE_CHUNK;
			if((Math.abs(px-xpos) > 2) || (Math.abs(py-ypos) > 2)){
				visible = false;
			}else{
				visible = true;
			}
		}
	}

	public void draw(Graphics g) {
		if(!dummy){
			this.updatePosition();
			if(visible){
				for(Block[] ba : blocks){
					for(Block b : ba){
						b.draw(g);
					}
				}
			}
		}
	}

	public void getTextures(LinkedList<Texture> l) {
		if(!dummy){
			updatePosition();
			if(visible){
				for(Block[] ba : blocks){
					for(Block b : ba){
						l.add(b);
					}
				}
			}
		}
	}

	/**
	 * gibt einen Block relativ zum Chunkanfang zurück!
	 * 
	 * @param x X-Position des Blocks 0 <= x <= 7
	 * @param y Y-Position des Blocks 0 <= x <= 7
	 * @return Der Block an der Position
	 */
	public Block getRelativeBlock(int x, int y){
		return blocks[x][y];
	}
	
}
