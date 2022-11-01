using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication1.Models;

namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FuelStationController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        public FuelStationController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        //get station object list
        [HttpGet("/all")]
        public JsonResult Get()
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var dbList = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").AsQueryable();

            return new JsonResult(dbList);
        }

        //get a station by city and station name
        [HttpGet("{city}/{stationName}")]
        public JsonResult GetStation(string city, string stationName)
        {
            System.Diagnostics.Debug.WriteLine(stationName);
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));
            var stationRes = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation")
                .Find(station => station.StationName == stationName).FirstOrDefault();
            System.Diagnostics.Debug.WriteLine(stationRes);
            return new JsonResult(stationRes);
            //return stationRes;
        }

        //increase Queue length
        [HttpPut("queueUpdate/{stationId}/increase")]
        public JsonResult increaseQueueLength(int stationId)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);

            var obj = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").Find(filter).FirstOrDefault();

            var updatedQueue = obj.Queue + 1;

            var update = Builders<FuelStation>.Update.Set("Queue", updatedQueue);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Queue length updated Successfully");
        }


        //----------------------working-------------------------------
        //update fuel status by admin
        [HttpPut("update/status/{stationId}/{status}")]
        public JsonResult UpdateFuelStatus(int stationId, bool status)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);

            var update = Builders<FuelStation>.Update.Set("Status", status);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Updated Fuel Status Successfully");
        }

        //---------------------------working---------------------------
        //update Fuel quantity by admin after fuel arrive to station
        [HttpPut("total/{stationId},{totalQuantity}")]
        public JsonResult UpdateFuelQuntityByAdmin(int stationId, int totalQuantity)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").Find(filter).FirstOrDefault();

            var update = Builders<FuelStation>.Update.Set("Quantity", totalQuantity);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Fuel Quantity updated Successfully");
        }

        //-----------------------working - reduce by passed quantity amount
        //update Fuel quantity (reduce)
        [HttpPut("fuel/{stationId},{quantity}")]
        public JsonResult UpdateFuelQuntity(int stationId, int quantity)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);

            var obj = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").Find(filter).FirstOrDefault();

            var updatedQuantity = obj.Quantity - quantity;
            var update = Builders<FuelStation>.Update.Set("Quantity", updatedQuantity);

            var updated = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Fuel Quantity updated Successfully");
        }

        

        //decrease queue length before fuel pump
        [HttpPut("notpump/{stationId}/{amount}")]
        public JsonResult decreaseQueueLengthWithoutFuel(int stationId, int amount)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);

            var obj = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").Find(filter).FirstOrDefault();

            var updatedQueue = obj.Queue - amount;

            var update = Builders<FuelStation>.Update.Set("Queue", updatedQueue);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Queue length updated Successfully without update fuel quantity");
        }

        //decrease queue length after fuel pump
        [HttpPut("pump/{stationId}/{amount}")]
        public JsonResult decreaseQueueLengthWithFuel(int stationId, int amount)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("FuelmgtappConn"));

            var filter = Builders<FuelStation>.Filter.Eq("StationId", stationId);

            //update fuel quntity
            JsonResult res = UpdateFuelQuntity(stationId, -4);

            var obj = dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").Find(filter).FirstOrDefault();
            var updatedQueue = obj.Queue - 1;

            var update = Builders<FuelStation>.Update.Set("Queue", updatedQueue);
            dbClient.GetDatabase("ead").GetCollection<FuelStation>("fuelstation").UpdateOne(filter, update);

            return new JsonResult("Queue length updated Successfully");
        }



    }
}
