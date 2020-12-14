using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.AspNetCore.Mvc;

namespace InstagramServer.Controllers
{
    [Route("[controller]")]
    public class UserDataController : Controller
    {
        private readonly IUserDataService _dataService;

        public UserDataController(IUserDataService dataService)
        {
            _dataService = dataService;
        }

        [HttpGet]
        public async Task<IEnumerable<UserData>> Get()
        {
            return await _dataService.GetAll();
        }

        [HttpGet("{id}", Name = "GetData")]
        public async Task<IActionResult> GetById(Guid id)
        {
            var userData = await _dataService.GetByUserId(id);
            if (userData == null)
            {
                return NotFound();
            }

            return Ok(userData);
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] UserData userData)
        {
            if (userData == null)
            {
                return BadRequest();
            }

            var addedData = await _dataService.Add(userData);

            return CreatedAtRoute("GetData", new {id = addedData.UserId}, addedData);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Put(Guid id, [FromBody] UserData userData)
        {
            if (userData == null)
            {
                return BadRequest();
            }

            var updateData = await _dataService.GetByUserId(id);

            if (updateData == null)
            {
                return NotFound();
            }

            userData.UserId = id;
            await _dataService.Update(userData);

            return NoContent();
        }
    }
}