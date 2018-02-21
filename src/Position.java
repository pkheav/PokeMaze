
public class Position {
	
		int row;
		int col;
		
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		
		
		/**
		 * Overrides hashCode() and equal so that two different Position objects with
		 * the same value for row and col are equal and produce the same hashCode.
		 * Useful when we use Position as a key in a HashMap
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			
			if(this == obj) {
				return true;
			}
			
			Position otherPos = (Position)obj;
			if (row == otherPos.row && col == otherPos.col) {
				return true;
			}
			return false;
		}
		
		/**
		 * Overrides hashCode() and equal so that two different Position objects with
		 * the same value for row and col are equal and produce the same hashCode.
		 * Useful when we use Position as a key in a HashMap
		 */
		@Override
		public int hashCode() {
			int hashCode = 17;
			hashCode = 31 * hashCode + row;
			hashCode = 31 * hashCode + col;
			return hashCode;
		}
}
