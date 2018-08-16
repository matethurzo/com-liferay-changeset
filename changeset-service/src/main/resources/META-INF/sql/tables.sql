create table ChangesetBaselineCollection (
	changesetBaselineCollectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null
);

create table ChangesetBaselineEntry (
	changesetBaselineEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	changesetBaselineCollectionId LONG,
	classNameId LONG,
	classPK LONG,
	version DOUBLE
);

create table ChangesetCollection (
	changesetCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table ChangesetEntry (
	changesetEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	changesetCollectionId LONG,
	classNameId LONG,
	classPK LONG
);