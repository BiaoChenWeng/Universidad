package tp1.p2.control;

//import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.RecordException;
import tp1.p2.view.Messages;

import java.io.*;

public class Record {
	private boolean newScore;
	private Level level;
	private int score;
	private final String FILE_NAME = "record.txt";
	private String[] record_data;
	private int level_record_pos;
	private final int DEFAULT_POS=-1;
	public Record(Level level) throws GameException {
		this.level_record_pos=DEFAULT_POS;
		this.level = level;
		this.score = 0;
		this.record_data = null;
		this.newScore = false;
		
		this.ReadFile();
		if (this.record_data != null) {			
			int i = 0;
			while (i < this.record_data.length && this.level_record_pos==DEFAULT_POS) {
				if (level.valueOfIgnoreCase(this.record_data[i])==this.level) {
					this.level_record_pos=i;
					this.score = Integer.parseInt(this.record_data[i + 1]);
				} else {
					i += 2;
				}
			}
		}
		
	}

	
	public String getScore() {
		return Messages.CURRENT_RECORD.formatted(this.level, this.score);
	}

	public void save_file(int score) throws GameException {
		if (this.score < score) {
			this.newScore = true;
			this.score = score;
			this.WriteFile();
		}
	}

	private void new_score() {		
		if(this.level_record_pos==DEFAULT_POS) {// no estaba en record el nivel
			String []aux = new String[this.record_data.length + 2];
			for (int j = 0; j < this.record_data.length; j++)
				aux[j] = this.record_data[j];
			aux[this.record_data.length] = this.level.name();
			aux[this.record_data.length + 1] = "" + this.score;
			this.record_data=aux;
		}
		else {// estaba en record y su pos fue guardado anteriormente 
			this.record_data[this.level_record_pos+1]=Integer.toString(this.score);
		}
	}

	private void ReadFile() throws GameException {
		String line = null;
		StringBuilder AllLines = new StringBuilder();
		try (FileReader read = new FileReader(FILE_NAME); BufferedReader inChars = new BufferedReader(read)) {

			while ((line = inChars.readLine()) != null) {
				AllLines.append(line);
				AllLines.append(" ");
			}

			if (AllLines != null && AllLines.toString() != "") {
				this.record_data = AllLines.toString().toLowerCase().replace(":", " ").trim().split("\\s+");
				for (int i = 0; i < this.record_data.length; i++) {
					if (i % 2 == 1) {
						Integer.parseInt(this.record_data[i]);
					} else {
						if (Level.valueOfIgnoreCase(this.record_data[i]) == null)
							throw new RecordException(Messages.RECORD_READ_ERROR);
					}
				}
			}
		} catch (NumberFormatException | IOException a) {
			throw new RecordException(Messages.RECORD_READ_ERROR, a);
		}		
	}

	private void WriteFile() throws GameException{

		try (FileWriter write = new FileWriter(FILE_NAME); BufferedWriter outChars = new BufferedWriter(write)) {

			if (this.record_data == null) {
				outChars.write(this.level.name() + ":" + this.score + "\n");//
			} else {
				this.new_score();
				for (int i = 0; i < this.record_data.length; i++) {
					outChars.write(this.record_data[i]);
					if (i % 2 == 0) {
						outChars.write(":");
					} else {
						outChars.write("\n");
					}

				}
			}
		}
		catch (IOException a ) {
			throw new RecordException(Messages.RECORD_WRITE_ERROR);
		}

	}

	public boolean newScore() {
		return this.newScore;
	}

}