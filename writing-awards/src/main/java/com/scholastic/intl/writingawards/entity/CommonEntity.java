package com.scholastic.intl.writingawards.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@MappedSuperclass
@Audited
public class CommonEntity {

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	protected Date createdDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	protected Date updatedDate;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="updated_by")
	private Long updatedBy;
	
	@Column(name="deleted")
	private Boolean deleted;
	@PrePersist
	@PreUpdate
	public void updateTimestamps() {
		if (createdDate == null) {
			createdDate = new Date();
		}
		updatedDate = new Date();
	}
	
	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(createdDate.getTime());
	}

	public Date getUpdatedDate() {
		return updatedDate == null ? null : new Date(updatedDate.getTime());
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate == null ? null : new Date(updatedDate.getTime());
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	

}
