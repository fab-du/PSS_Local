package de.app.model;

import java.util.Date;

public class KeyPair {
		Long id;
		Date createdAt;
		
		String pubkey;
		String prikey;

		String salt;

		public String getPubkey() {
			return pubkey;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public void setPubkey(String pubkey) {
			this.pubkey = pubkey;
		}
		public String getPrikey() {
			return prikey;
		}
		public void setPrikey(String prikey) {
			this.prikey = prikey;
		}
		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
		@Override
		public String toString() {
			return "KeyPair [id=" + id + ", createdAt=" + createdAt + ", pubkey=" + pubkey + ", prikey=" + prikey
					+ ", salt=" + salt + "]";
		}


}

