package com.example.stay_hi_fi.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class AuditEntity {


	@Column(name="created_by")
	@CreatedBy
	protected String createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name="created_at")
	protected Date createdDate;

	@Column(name="updated_by")
	@LastModifiedBy
	protected String updatedBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedBy
	@Column(name="updated_at")
	protected Date updatedDate;



}
