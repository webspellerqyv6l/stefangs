﻿using System;
using System.Globalization;
using System.Net;
using System.Web.Http;
using System.Web.Http.Description;
using Nop.Core;
using Nop.Core.Domain.Directory;
using Nop.Core.Domain.Stores;
using Nop.Plugin.Api.Attributes;
using Nop.Plugin.Api.DTOs.Stores;
using Nop.Plugin.Api.JSON.ActionResults;
using Nop.Plugin.Api.MappingExtensions;
using Nop.Plugin.Api.Serializers;
using Nop.Services.Customers;
using Nop.Services.Directory;
using Nop.Services.Discounts;
using Nop.Services.Localization;
using Nop.Services.Logging;
using Nop.Services.Media;
using Nop.Services.Security;
using Nop.Services.Stores;
using static System.Decimal;

namespace Nop.Plugin.Api.Controllers
{
    [BearerTokenAuthorize]
    public class StoreController : BaseApiController
    {
        private IStoreContext _storeContext;
        private readonly CurrencySettings _currencySettings;
        private readonly ICurrencyService _currencyService;

        public StoreController(IJsonFieldsSerializer jsonFieldsSerializer,
            IAclService aclService,
            ICustomerService customerService,
            IStoreMappingService storeMappingService,
            IStoreService storeService,
            IDiscountService discountService,
            ICustomerActivityService customerActivityService,
            ILocalizationService localizationService,
            IPictureService pictureService,
            IStoreContext storeContext,
            CurrencySettings currencySettings,
            ICurrencyService currencyService)
            : base(jsonFieldsSerializer,
                  aclService,
                  customerService,
                  storeMappingService,
                  storeService,
                  discountService,
                  customerActivityService,
                  localizationService,
                  pictureService)
        {
            _storeContext = storeContext;
            _currencySettings = currencySettings;
            _currencyService = currencyService;
        }

        /// <summary>
        /// Retrieve category by spcified id
        /// </summary>
        /// <param name="fields">Fields from the category you want your json to contain</param>
        /// <response code="200">OK</response>
        /// <response code="404">Not Found</response>
        /// <response code="401">Unauthorized</response>
        [HttpGet]
        [ResponseType(typeof(StoresRootObject))]
        [GetRequestsErrorInterceptorActionFilter]
        public IHttpActionResult GetCurrentStore(string fields = "")
        {
            Store store = _storeContext.CurrentStore;

            if (store == null)
            {
                return Error(HttpStatusCode.NotFound, "store", "store not found");
            }

            StoreDto storeDto = store.ToDto();

            Currency primaryCurrency = _currencyService.GetCurrencyById(_currencySettings.PrimaryStoreCurrencyId);

            if (!String.IsNullOrEmpty(primaryCurrency.DisplayLocale))
            {
                storeDto.PrimaryCurrencyDisplayLocale = primaryCurrency.DisplayLocale;
            }

            var storesRootObject = new StoresRootObject();

            storesRootObject.Stores.Add(storeDto);

            var json = _jsonFieldsSerializer.Serialize(storesRootObject, fields);

            return new RawJsonActionResult(json);
        }
    }
}
