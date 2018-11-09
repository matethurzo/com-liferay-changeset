/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.changeset.rest.internal.resource;

import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.rest.internal.dto.ChangesetCollectionDTO;
import com.liferay.changeset.rest.internal.dto.ResponseDTO;
import com.liferay.changeset.rest.internal.dto.StatusDTO;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=Changeset.Rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true"
	},
	service = Object.class
)
public class ChangesetResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ChangesetCollectionDTO addChangesetCollection(
		@QueryParam("name") String name) {

		try {
			Optional<ChangesetCollection> changesetCollectionOptional =
				_changesetManager.create(name, "");

			return changesetCollectionOptional.map(
				ChangesetCollectionDTO::of
			).orElseThrow(
				() -> new WebApplicationException(
					"Unable to add new changeset!")
			);
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@Path("/checkout/{changesetId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkout(@PathParam("changesetId") long changesetId) {
		try {
			_changesetManager.checkout(changesetId);

			Optional<Long> currentChangesetCollectionId =
				_changesetManager.getCurrentChangesetCollectionId();

			return Response.ok(
				new ResponseDTO(
					"The currently active changeset is " +
						currentChangesetCollectionId.orElse(0L))
			).build();
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@Path("/checkout-production")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkoutProd() {
		try {
			_changesetManager.checkout(
				ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID);

			return Response.ok(
				new ResponseDTO(
					"The currently active changeset is the Production " +
						"environment")
			).build();
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@Path("/disable")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response disable() {
		try {
			if (!_changesetManager.isChangesetEnabled()) {
				throw new WebApplicationException(
					"Changeset is already disabled");
			}

			_changesetManager.disableChangesets();

			return Response.ok(
				new ResponseDTO("Changeset Disabled...")
			).build();
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@Path("/enable")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response enable() {
		try {
			if (_changesetManager.isChangesetEnabled()) {
				throw new WebApplicationException(
					"Changeset is already enabled");
			}

			_changesetManager.enableChangesets();

			return Response.ok(
				new ResponseDTO("Changeset Enabled...")
			).build();
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ChangesetCollectionDTO> getChangesetCollections() {
		List<ChangesetCollection> changesetCollections =
			_changesetCollectionLocalService.getChangesetCollections(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<ChangesetCollection> changesetCollectionStream =
			changesetCollections.stream();

		return changesetCollectionStream.map(
			ChangesetCollectionDTO::of
		).collect(
			Collectors.toList()
		);
	}

	@GET
	@Path("/{changesetId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChangesetCollectionDTO getChangesetCollections(
		@PathParam("changesetId") long changesetId) {

		try {
			ChangesetCollection changesetCollection =
				_changesetCollectionLocalService.getChangesetCollection(
					changesetId);

			return ChangesetCollectionDTO.of(changesetCollection);
		}
		catch (Exception e) {
			throw new WebApplicationException(
				"Unable to find changeset with id " + changesetId, e,
				Response.Status.BAD_REQUEST);
		}
	}

	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	public StatusDTO getStatus() {
		StatusDTO statusDTO = new StatusDTO();

		boolean changesetEnabled = _changesetManager.isChangesetEnabled();

		statusDTO.setChangesetEnabled(changesetEnabled);

		if (changesetEnabled) {
			Optional<Long> changesetCollectionIdOptional =
				_changesetManager.getCurrentChangesetCollectionId();

			ChangesetCollectionDTO changesetCollectionDTO =
				changesetCollectionIdOptional.map(
					_changesetCollectionLocalService::fetchChangesetCollection
				).map(
					ChangesetCollectionDTO::of
				).orElse(
					null
				);

			statusDTO.setActiveChangeset(changesetCollectionDTO);
		}

		return statusDTO;
	}

	@Path("/publish/{changesetId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response publish(@PathParam("changesetId") long changesetId) {
		try {
			if (!_changesetManager.isChangesetEnabled()) {
				throw new WebApplicationException("Changeset is not enabled!");
			}

			_changesetManager.publish(changesetId);

			ChangesetCollection changesetCollection =
				_changesetCollectionLocalService.getChangesetCollection(
					changesetId);

			return Response.ok(
				new ResponseDTO(
					changesetCollection.getName() +
						" has been successfully published to the Production " +
							"environment")
			).build();
		}
		catch (Exception e) {
			throw new WebApplicationException(e.getMessage(), e);
		}
	}

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetManager _changesetManager;

}