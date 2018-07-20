package org.sunbird.integration.test.page;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadPageSettingsTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_READ_PAGE_SETTINGS_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testReadPageSettingsFailureWithoutAccessToken";
  public static final String TEST_NAME_READ_PAGE_SETTINGS_SUCCESS_WITH_INVALID_PAGE_ID =
      "testReadPageSettingsSuccessWithInvalidPageId";

  public static final String TEMPLATE_DIR = "templates/page/read";

  private String getReadPageUrl() {
    return getLmsApiUriPath("/api/data/v1/page/read", "/v1/page/read");
  }

  @DataProvider(name = "readPageSettingsFailureDataProvider")
  public Object[][] readPageSettingsFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_READ_PAGE_SETTINGS_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED,
      },
      new Object[] {
        TEST_NAME_READ_PAGE_SETTINGS_SUCCESS_WITH_INVALID_PAGE_ID, false, HttpStatus.NOT_FOUND,
      },
    };
  }

  @Test(dataProvider = "readPageSettingsFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testReadPageSettingsFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getAuthToken(this, isAuthRequired);

    String url = getReadPageUrl() + "/invalid";

    performGetTest(
        this, TEMPLATE_DIR, testName, url, isAuthRequired, httpStatusCode, RESPONSE_JSON);
  }
}
