package interactivespaces.master.server.services.internal;

import static com.google.common.io.Closeables.closeQuietly;

import interactivespaces.InteractiveSpacesException;
import interactivespaces.common.ResourceRepositoryUploadChannel;
import interactivespaces.master.server.services.ActiveSpaceController;
import interactivespaces.resource.repository.ResourceRepositoryServer;
import interactivespaces.resource.repository.ResourceRepositoryStorageManager;
import interactivespaces.util.data.resource.CopyableResource;
import interactivespaces.util.data.resource.CopyableResourceListener;
import org.apache.commons.logging.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Basic data bundle manager. Needs to be sub-classed to provide the necessary communication.
 *
 * @author Trevor Pering
 */
public abstract class BasicMasterDataBundleManager implements MasterDataBundleManager {

  /**
   * Static version identifier to use for unversioned bundles.
   */
  protected static final String DATA_BUNDLE_STATIC_VERSION = "noversion";

  /**
   * Resource repository server to use.
   */
  protected ResourceRepositoryServer resourceRepositoryServer;

  /**
   * Logger for the manager.
   */
  protected Log log;

  @Override
  public void startup() {
    resourceRepositoryServer.registerResourceUploadListener(
        ResourceRepositoryUploadChannel.DATA_BUNDLE_UPLOAD,
        new CopyableResourceListener() {
          @Override
          public void onUploadSuccess(String resourceId, CopyableResource resourceUpload) {
            handleDataBundleUploadSuccess(resourceId, resourceUpload);
          }
        });
  }

  @Override
  public void shutdown() {
    resourceRepositoryServer.removeResourceUploadListener(
        ResourceRepositoryUploadChannel.DATA_BUNDLE_UPLOAD);
  }

  @Override
  public void captureControllerDataBundle(ActiveSpaceController controller) {
    String uuid = controller.getController().getUuid();
    String destinationUri = resourceRepositoryServer.getResourceUri(
        ResourceRepositoryStorageManager.RESOURCE_CATEGORY_DATA, uuid, DATA_BUNDLE_STATIC_VERSION);
    sendControllerDataBundleCaptureRequest(controller, destinationUri);
  }

  /**
   * Send a controller data bundle capture request. Abstract method to be overridden by the a
   * communication-specific implementation.
   *
   * @param controller
   *          controller to capture from
   * @param destinationUri
   *          destination to use for capture request
   */
  protected abstract void sendControllerDataBundleCaptureRequest(ActiveSpaceController controller,
      String destinationUri);

  @Override
  public void restoreControllerDataBundle(ActiveSpaceController controller) {
    String uuid = controller.getController().getUuid();
    String sourceUri = resourceRepositoryServer.getResourceUri(
        ResourceRepositoryStorageManager.RESOURCE_CATEGORY_DATA, uuid, DATA_BUNDLE_STATIC_VERSION);
    sendControllerDataBundleRestoreRequest(controller, sourceUri);
  }

  /**
   * Send a controller data bundle restore request. Abstract method to be overridden by the a
   * communication-specific implementation.
   *
   * @param controller
   *          controller to capture from
   * @param sourceUri
   *          source to use for restore request
   */
  protected abstract void sendControllerDataBundleRestoreRequest(ActiveSpaceController controller,
      String sourceUri);

  /**
  * Handle a successful upload to the data bundle upload repository.
  *
  * @param controllerUuid
  *            the controllerUuid that was uploaded.
  * @param resourceUpload
  *            the actual resource that was uploaded.
  */
  private void handleDataBundleUploadSuccess(String controllerUuid,
      CopyableResource resourceUpload) {
    OutputStream outputStream = null;
    try {
      outputStream = resourceRepositoryServer.createResourceOutputStream(
          ResourceRepositoryStorageManager.RESOURCE_CATEGORY_DATA,
          controllerUuid, DATA_BUNDLE_STATIC_VERSION);
      resourceUpload.copyTo(outputStream);
      outputStream.close();
    } catch (IOException e) {
      throw new InteractiveSpacesException("Unable to upload bundle for " + controllerUuid, e);
    } finally {
      closeQuietly(outputStream);
    }
  }

  /**
   * Set the resource repository to use for this request.
   *
   * @param resourceRepositoryServer
   *          resource repository server to utilize
   */
  public void setRepositoryServer(ResourceRepositoryServer resourceRepositoryServer) {
    this.resourceRepositoryServer = resourceRepositoryServer;
  }

  /**
   * @param log
   *          the log to set
   */
  public void setLog(Log log) {
    this.log = log;
  }

}
